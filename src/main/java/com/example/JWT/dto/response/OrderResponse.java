package com.example.JWT.dto.response;

import com.example.JWT.model.Order;

import java.util.List;

public class OrderResponse {

    private String orderId;
    private Long createdAt;
    private String status;
    private Double totalAmount;
    private List<Order.OrderItem> items;

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.createdAt = order.getCreatedAt();
        this.status = order.getStatus();
        this.totalAmount = order.getTotalAmount();
        this.items = order.getItems();
    }

    public String getOrderId() { return orderId; }
    public Long getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
    public Double getTotalAmount() { return totalAmount; }
    public List<Order.OrderItem> getItems() { return items; }
}