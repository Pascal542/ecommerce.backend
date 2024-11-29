package com.pascal.ecommerce.backend.service;

import com.pascal.ecommerce.backend.enums.OrderStatus;
import com.pascal.ecommerce.backend.model.CartItem;
import com.pascal.ecommerce.backend.model.FinalOrder;
import com.pascal.ecommerce.backend.repository.CartItemRepository;
import com.pascal.ecommerce.backend.repository.FinalOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinalOrderService {

    private final CartItemRepository cartItemRepository;
    private final FinalOrderRepository finalOrderRepository;

    public FinalOrder createFinalOrder(Long userId) {

        // Get all cart items of the user
        List<CartItem> userCartItems = cartItemRepository.findAllByUserId(userId);

        // Sum all cart total amounts
        BigDecimal totalOrderAmount = userCartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        // Crear y poblar la entidad FinalOrder
        FinalOrder finalOrder = new FinalOrder();
        finalOrder.setOrderDate(LocalDate.now());
        finalOrder.setOrderTotalAmount(totalOrderAmount);
        finalOrder.setOrderStatus(OrderStatus.PENDING);
        finalOrder.setUserId(userId);

        // Guardar y devolver FinalOrder
        return finalOrderRepository.save(finalOrder);
    }

    public List<FinalOrder> getAllFinalOrders() {
        return finalOrderRepository.findAll();
    }
}
