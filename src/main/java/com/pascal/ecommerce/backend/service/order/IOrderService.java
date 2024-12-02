package com.pascal.ecommerce.backend.service.order;

import com.pascal.ecommerce.backend.dto.OrderDto;
import com.pascal.ecommerce.backend.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
