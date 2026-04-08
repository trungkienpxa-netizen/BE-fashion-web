package edu.thanglong.usecase.cart;

import edu.thanglong.domain.exception.BusinessRuleException;
import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.model.Cart;
import edu.thanglong.domain.model.Product;
import edu.thanglong.domain.repository.CartRepository;
import edu.thanglong.domain.repository.ProductRepository;
import edu.thanglong.presentation.dto.request.AddToCartRequest;
import edu.thanglong.presentation.dto.request.UpdateCartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartUseCaseImpl implements CartUseCase {

    private final CartRepository    cartRepository;
    private final ProductRepository productRepository;

    @Override
    public Cart getMyCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElse(Cart.builder()
                        .userId(userId)
                        .items(new java.util.ArrayList<>())
                        .updatedAt(LocalDateTime.now())
                        .build());
    }

    @Override
    public Cart addItem(String userId, AddToCartRequest req) {
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", req.getProductId()));

        Product.Variant variant = product.getVariants().stream()
                .filter(v -> v.getSku().equals(req.getVariantSku()))
                .findFirst()
                .orElseThrow(() -> new BusinessRuleException(
                    "Variant không tồn tại: " + req.getVariantSku()));

        if (variant.getStockQuantity() < req.getQuantity())
            throw new BusinessRuleException("Sản phẩm không đủ số lượng trong kho");

        Cart cart = cartRepository.findByUserId(userId)
                .orElse(Cart.builder()
                        .userId(userId)
                        .items(new java.util.ArrayList<>())
                        .build());

        cart.addItem(Cart.Item.builder()
                .productId(req.getProductId())
                .variantSku(req.getVariantSku())
                .quantity(req.getQuantity())
                .build());

        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateItem(String userId, UpdateCartItemRequest req) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessRuleException("Giỏ hàng trống"));

        boolean exists = cart.getItems().stream()
                .anyMatch(i -> i.getVariantSku().equals(req.getVariantSku()));

        if (!exists)
            throw new BusinessRuleException("Sản phẩm không có trong giỏ hàng");

        if (req.getQuantity() <= 0) {
            cart.removeItem(req.getVariantSku());
        } else {
            cart.getItems().stream()
                    .filter(i -> i.getVariantSku().equals(req.getVariantSku()))
                    .findFirst()
                    .ifPresent(i -> i.setQuantity(req.getQuantity()));
        }

        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItem(String userId, String variantSku) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessRuleException("Giỏ hàng trống"));

        cart.removeItem(variantSku);
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(String userId) {
        cartRepository.findByUserId(userId).ifPresent(cart -> {
            cart.clear();
            cart.setUpdatedAt(LocalDateTime.now());
            cartRepository.save(cart);
        });
    }
}