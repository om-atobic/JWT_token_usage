package com.example.JWT.model;

import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

import java.util.List;

@ToString
@DynamoDbBean
public class Order {

    private String OrderId;           // partition key
    private Long createdAt;           // sort Key

    // user info
    private String userId;            // gsi partition Key

    // order details
    private List<OrderItem> items;    // List of products in order
    private String status;            // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    private Double totalAmount;       // Total order amount

    // Shipping Information
    private String shippingAddress;
    private String paymentMethod;

    // Metadata
    private Long updatedAt;
    private String cancelReason;

    // Primary Keys
    @DynamoDbPartitionKey
    @DynamoDbAttribute("OrderId")
    public String getOrderId() { return OrderId; }
    public void setOrderId(String OrderId) { this.OrderId = OrderId; }

    @DynamoDbSortKey
    @DynamoDbSecondarySortKey(indexNames = "userId-createdAt-index")
    @DynamoDbAttribute("createdAt")
    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    // GSI for querying user's orders: userId-createdAt-index

    @DynamoDbSecondaryPartitionKey(indexNames = "userId-createdAt-index")
    @DynamoDbAttribute("userId")
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    // Order details
    @DynamoDbAttribute("items")
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    @DynamoDbAttribute("status")
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @DynamoDbAttribute("totalAmount")
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    @DynamoDbAttribute("shippingAddress")
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    @DynamoDbAttribute("paymentMethod")
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    @DynamoDbAttribute("updatedAt")
    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }

    @DynamoDbAttribute("cancelReason")
    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }


    // Inner class for order items
    @DynamoDbBean
    public static class OrderItem {
        private String productId;
        private String productName;
        private Integer quantity;
        private Double price;
        private Double subtotal;

        public OrderItem() {}

        public OrderItem(String productId, String productName, Integer quantity, Double price) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.subtotal = quantity * price;
        }

        @DynamoDbAttribute("productId")
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }

        @DynamoDbAttribute("productName")
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        @DynamoDbAttribute("quantity")
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        @DynamoDbAttribute("price")
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }

        @DynamoDbAttribute("subtotal")
        public Double getSubtotal() { return subtotal; }
        public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    }
}
