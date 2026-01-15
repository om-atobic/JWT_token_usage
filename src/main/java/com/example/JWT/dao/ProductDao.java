package com.example.JWT.dao;

import com.example.JWT.model.Product;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Repository
public class ProductDao {
    private final DynamoDbTable<Product> productTable;
    public ProductDao(DynamoDbTable<Product> productTable) {
        this.productTable = productTable;
    }
    public Product getById (String productId) {
        return productTable.getItem(
                Key.builder().partitionValue(productId).build()
        );
    }
}
