package com.example.JWT.controller;

import com.example.JWT.dto.request.CreateOrderRequest;
import com.example.JWT.dto.request.cancleOrderRequest;
import com.example.JWT.dto.response.OrderResponse;
import com.example.JWT.model.Order;
import com.example.JWT.model.Role;
import com.example.JWT.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest orderRequest,
                                              HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Role userRole = (Role) request.getAttribute("userRole");

        //for now i am keeping the static user id since this one is randomly generated and will change
        userId = "om";
        //call the service to store the given data in db for now no validation on input data
        Order order = orderService.createOrders(
                orderRequest.getProductIds(),
                orderRequest.getQuantities(),
                orderRequest.getShippingAddress(),
                orderRequest.getPaymentMethod(),
                userId
        );
        return ResponseEntity.ok("Order created for user: " + userId +
                ", products: " + orderRequest.getProductIds().size() +
                ", address: " + orderRequest.getShippingAddress());
    }
    @PutMapping("/cancel")
    public ResponseEntity<String> cancelOrder(@RequestBody cancleOrderRequest cancleOrderRequest,
                                              HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        userId = "om"; //for now userId is static
        //should cancel the order if status is not delivered

        boolean response = orderService.cancelOrder(cancleOrderRequest.getOrderId(), userId, cancleOrderRequest.getReason());
        if (response == true) {
            return ResponseEntity.ok("Order " + cancleOrderRequest.getOrderId() + " cancelled by user: " + userId);
        }
        //Here either the status of order not allow to cancle the ordere or the orderId was invalid or the userId not match with userId
        return ResponseEntity.ok("Order " + cancleOrderRequest.getOrderId() + " cannot be cancelled by user: " + userId);


    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderResponse>> getOrders(@RequestParam(required = false)String status, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        //for now userId is static
        userId = "om";

        List<Order> orders = orderService.getOrdersByUserId(userId,status);

        List<OrderResponse> response = orders.stream()
                .map(OrderResponse::new)
                .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/status/{orderId}")
    public ResponseEntity<Order> getOrderStatus(@PathVariable String orderId,
                                                HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        //for now userId is static
        userId = "om";
        String status = null;
        Order order = orderService.getOrderByOrderId(orderId);

        return ResponseEntity.ok(order);
    }
    @PutMapping("/update/status")
    public ResponseEntity<String> updateOrderStatus(@RequestParam String orderId,
                                                    @RequestParam String status,
                                                    HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        userId = "om"; //for now userId is static
        //should cancel the order if status is not delivered
        boolean response = orderService.updateOrderStatus(orderId, userId, status);
        if (response == true) {
            return ResponseEntity.ok("Order " + orderId + " updated to " + status + " by user: " + userId);
        }
        return ResponseEntity.ok("Order " + orderId + " cannot be updated to " + status + " by user: " + userId);
    }


}