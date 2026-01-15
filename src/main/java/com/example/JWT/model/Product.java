package com.example.JWT.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class Product {
    private String productId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Integer stockQuantity;
    private String status;
    private String createdAt;

    @DynamoDbPartitionKey
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    @DynamoDbAttribute("name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @DynamoDbAttribute("description")
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @DynamoDbAttribute("price")
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    @DynamoDbSecondaryPartitionKey(indexNames = "category-createdAt-index")
    @DynamoDbAttribute("category")
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @DynamoDbAttribute("stockQuantity")
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    @DynamoDbAttribute("status")
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @DynamoDbAttribute("createdAt")
    public String getCreatedBy() { return createdAt; }
    public void setCreatedBy(String createdBy) { this.createdAt = createdAt; }


}
