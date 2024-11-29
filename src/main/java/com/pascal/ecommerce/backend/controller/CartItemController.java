package com.pascal.ecommerce.backend.controller;

import com.pascal.ecommerce.backend.dto.CartItemDto;
import com.pascal.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.pascal.ecommerce.backend.model.CartItem;
import com.pascal.ecommerce.backend.response.ApiResponse;
import com.pascal.ecommerce.backend.service.cart.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
@CrossOrigin(origins = "http://localhost:5173")
public class CartItemController {
    private final ICartItemService cartItemService;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId,
                                                     @RequestParam Integer quantity,
                                                     @RequestParam Long userId) {
        try {
            cartItemService.addCartItem(productId, quantity, userId);
            return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/user/{userId}/cartItem/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable Long userId,
                                                      @PathVariable Long itemId) {
        try {
            cartItemService.removeCartItem(itemId, userId);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cartItem/{itemId}/user/{userId}/update")
    public ResponseEntity<ApiResponse> updateCartItemQuantity(@PathVariable Long itemId,
                                                              @PathVariable Long userId,
                                                              @RequestParam Integer quantity) {
        try {
            CartItem cartItem = cartItemService.updateCartItemQuantity(itemId, userId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item Success", cartItem));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}/cartItems")
    public ResponseEntity<ApiResponse> getCartItemsByUserId(@PathVariable Long userId) {
        try {
            List<CartItem> cartItems = cartItemService.getCartItemsByUserId(userId);
            List<CartItemDto> cartItemDTOs = cartItems.stream()
                    .map(this::mapToCartItemDTO)
                    .collect(Collectors.toList()); // Convert CartItems to CartItemDtos>
            return ResponseEntity.ok(new ApiResponse("Success", cartItemDTOs));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
