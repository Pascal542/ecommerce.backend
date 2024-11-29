package com.pascal.ecommerce.backend.service.cart;

import com.pascal.ecommerce.backend.dto.CartItemDto;
import com.pascal.ecommerce.backend.dto.ProductCartItemDto;
import com.pascal.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.pascal.ecommerce.backend.model.CartItem;
import com.pascal.ecommerce.backend.model.Product;
import com.pascal.ecommerce.backend.repository.CartItemRepository;
import com.pascal.ecommerce.backend.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;


    @Override
    public void addCartItem(Long productId, int quantity, Long userId) {
        // Get the product
        Product product = productService.getProductById(productId);

        CartItem cartItem = new CartItem();

        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setUserId(userId);
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void removeCartItem(Long cartItemId, Long userId) {
        CartItem cartItem = cartItemRepository.findByIdAndUserId(cartItemId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found for id: " + cartItemId + " and userId: " + userId));
        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public CartItem updateCartItemQuantity(Long cartItemId, Long userId, int quantity) {
        CartItem cartItem = cartItemRepository.findByIdAndUserId(cartItemId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found for id: " + cartItemId + " and userId: " + userId));
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        return cartItemRepository.save(cartItem); // Persistir los cambios
    }

    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findAllByUserId(userId);
    }
}
