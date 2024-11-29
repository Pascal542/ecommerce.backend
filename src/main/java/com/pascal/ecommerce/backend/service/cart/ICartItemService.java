package com.pascal.ecommerce.backend.service.cart;

import com.pascal.ecommerce.backend.model.CartItem;

import java.util.List;

public interface ICartItemService {
    void addCartItem(Long productId, int quantity, Long userId);
    void removeCartItem(Long cartItemId, Long userId);
    CartItem updateCartItemQuantity(Long cartItemId, Long userId, int quantity);
    List<CartItem> getCartItemsByUserId(Long userId);
}
