package com.pascal.ecommerce.backend.request;

import com.pascal.ecommerce.backend.enums.OrderStatus;
import lombok.Data;

@Data
public class FinalOrderRequest {
    private Long orderId;
    private OrderStatus orderStatus;
}
