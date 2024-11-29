package com.pascal.ecommerce.backend.model;

import com.pascal.ecommerce.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "final_orders")
public class FinalOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal orderTotalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Long userId;
}
