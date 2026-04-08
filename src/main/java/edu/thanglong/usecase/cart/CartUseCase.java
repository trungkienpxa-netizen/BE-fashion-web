package edu.thanglong.usecase.cart;

import edu.thanglong.domain.model.Cart;
import edu.thanglong.presentation.dto.request.AddToCartRequest;
import edu.thanglong.presentation.dto.request.UpdateCartItemRequest;

public interface CartUseCase {
    Cart getMyCart(String userId);
    Cart addItem(String userId, AddToCartRequest request);
    Cart updateItem(String userId, UpdateCartItemRequest request);
    Cart removeItem(String userId, String variantSku);
    void clearCart(String userId);
}