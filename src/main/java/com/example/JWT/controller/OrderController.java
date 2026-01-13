package com.example.JWT.controller;

import com.example.JWT.dto.request.CreateOrderRequest;
import com.example.JWT.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
public class OrderController {

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest orderRequest,
                                       HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Role userRole = (Role) request.getAttribute("userRole");
        
        return ResponseEntity.ok("Order created for user: " + userId + 
                               ", products: " + orderRequest.getProductIds().size() + 
                               ", address: " + orderRequest.getShippingAddress());
    }

    @GetMapping("/status")
    public ResponseEntity<?> getOrderStatus(@PathVariable String orderId,
                                            HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        return ResponseEntity.ok("Order " + orderId + " for user: " + userId);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getOrders(@RequestParam(required = false) String status,
                                     @RequestParam(defaultValue = "0") int page,
                                     HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        
        return ResponseEntity.ok("Orders for user: " + userId + 
                               ", status: " + status + 
                               ", page: " + page);
    }


    @PutMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId,
                                       @RequestParam(required = false) String reason,
                                       HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        
        return ResponseEntity.ok("Order " + orderId + " cancelled by user: " + userId + 
                               ", reason: " + reason);
    }
}