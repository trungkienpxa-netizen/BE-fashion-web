package edu.thanglong.presentation.controller;

import edu.thanglong.presentation.dto.request.AddToCartRequest;
import edu.thanglong.presentation.dto.request.UpdateCartItemRequest;
import edu.thanglong.presentation.dto.response.ApiResponse;
import edu.thanglong.presentation.dto.response.CartResponse;
import edu.thanglong.usecase.cart.CartUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartUseCase cartUseCase;

    @GetMapping
    public ApiResponse<CartResponse> getMyCart(Authentication authentication) {
        return ApiResponse.success(
            CartResponse.from(cartUseCase.getMyCart((String) authentication.getPrincipal()))
        );
    }

    @PostMapping("/items")
    public ApiResponse<CartResponse> addItem(Authentication authentication,
                                             @Valid @RequestBody AddToCartRequest request) {
        return ApiResponse.success(
            CartResponse.from(cartUseCase.addItem(
                (String) authentication.getPrincipal(), request))
        );
    }

    @PutMapping("/items")
    public ApiResponse<CartResponse> updateItem(Authentication authentication,
                                                @Valid @RequestBody UpdateCartItemRequest request) {
        return ApiResponse.success(
            CartResponse.from(cartUseCase.updateItem(
                (String) authentication.getPrincipal(), request))
        );
    }

    @DeleteMapping("/items/{variantSku}")
    public ApiResponse<CartResponse> removeItem(Authentication authentication,
                                                @PathVariable String variantSku) {
        return ApiResponse.success(
            CartResponse.from(cartUseCase.removeItem(
                (String) authentication.getPrincipal(), variantSku))
        );
    }

    @DeleteMapping
    public ApiResponse<Void> clearCart(Authentication authentication) {
        cartUseCase.clearCart((String) authentication.getPrincipal());
        return ApiResponse.success(null);
    }
}