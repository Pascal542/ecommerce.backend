package com.pascal.ecommerce.backend.controller;

import com.pascal.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.pascal.ecommerce.backend.model.FinalOrder;
import com.pascal.ecommerce.backend.response.ApiResponse;
import com.pascal.ecommerce.backend.service.FinalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/final-orders")
@CrossOrigin(origins = "http://localhost:5173")
public class FinalOrderController {

    private final FinalOrderService finalOrderService;

    @PostMapping("/{userId}/create")
    public ResponseEntity<ApiResponse> createFinalOrder(@PathVariable Long userId) {
        try {
            FinalOrder finalOrder = finalOrderService.createFinalOrder(userId);
            return ResponseEntity.ok(new ApiResponse(
                    "Final Order Created Successfully!", finalOrder));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Resource Not Found",e.getMessage()));
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllFinalOrders() {
        try {
            List<FinalOrder> finalOrders = finalOrderService.getAllFinalOrders();
            return ResponseEntity.ok(new ApiResponse(
                    "Final Orders Fetched Successfully!", finalOrders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Resource Not Found",e.getMessage()));
        }
    }

    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity<ApiResponse> getFinalOrderById(@PathVariable Long userId) {
        try {
            List<FinalOrder> finalOrders = finalOrderService.getFinalOrdersByUserId(userId);
            return ResponseEntity.ok(new ApiResponse(
                    "Final Orders Fetched Successfully!", finalOrders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Resource Not Found",e.getMessage()));
        }
    }
}
