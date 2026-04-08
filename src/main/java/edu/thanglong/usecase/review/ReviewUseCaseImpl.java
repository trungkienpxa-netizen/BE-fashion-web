package edu.thanglong.usecase.review;

import edu.thanglong.domain.exception.BusinessRuleException;
import edu.thanglong.domain.exception.ResourceNotFoundException;
import edu.thanglong.domain.exception.UnauthorizedException;
import edu.thanglong.domain.model.Order;
import edu.thanglong.domain.model.Review;
import edu.thanglong.domain.repository.OrderRepository;
import edu.thanglong.domain.repository.ProductRepository;
import edu.thanglong.domain.repository.ReviewRepository;
import edu.thanglong.presentation.dto.request.CreateReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewUseCaseImpl implements ReviewUseCase {

    private final ReviewRepository   reviewRepository;
    private final OrderRepository    orderRepository;
    private final ProductRepository  productRepository;

    @Override
    public Review create(String userId, CreateReviewRequest req) {
        productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", req.getProductId()));

        // Kiểm tra đã mua hàng chưa
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", req.getOrderId()));

        if (!order.getUserId().equals(userId))
            throw new UnauthorizedException("Bạn không có quyền review đơn hàng này");

        if (order.getStatus() != Order.OrderStatus.DELIVERED)
            throw new BusinessRuleException("Chỉ có thể review sau khi đơn hàng đã giao");

        boolean productInOrder = order.getItems().stream()
                .anyMatch(i -> {
                    try {
                        return productRepository.findById(req.getProductId())
                                .map(p -> p.getVariants().stream()
                                        .anyMatch(v -> v.getSku().equals(i.getVariantSku())))
                                .orElse(false);
                    } catch (Exception e) {
                        return false;
                    }
                });

        if (!productInOrder)
            throw new BusinessRuleException("Sản phẩm không có trong đơn hàng này");

        if (reviewRepository.existsByUserIdAndOrderId(userId, req.getOrderId()))
            throw new BusinessRuleException("Bạn đã review đơn hàng này rồi");

        return reviewRepository.save(Review.builder()
                .userId(userId)
                .productId(req.getProductId())
                .orderId(req.getOrderId())
                .rating(req.getRating())
                .comment(req.getComment())
                .verifiedPurchase(true)
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    public List<Review> getByProductId(String productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public List<Review> getMyReviews(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public void delete(String userId, String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", reviewId));
        if (!review.getUserId().equals(userId))
            throw new UnauthorizedException("Bạn không có quyền xóa review này");
        reviewRepository.deleteById(reviewId);
    }
}