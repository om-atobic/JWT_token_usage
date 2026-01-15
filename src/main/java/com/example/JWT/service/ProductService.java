package com.example.JWT.service;

import com.example.JWT.dto.response.ProductListResponse;
import com.example.JWT.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private DynamoDbTable<Product> productTable;

    public ProductListResponse getAllProducts(int page, int size) {
        List<Product> allProducts = productTable.scan().items().stream().collect(Collectors.toList());
        return buildResponse(allProducts, page, size);
    }

    public ProductListResponse getProductsByCategory(String category, int page, int size) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":category", AttributeValue.builder().s(category).build());

        Expression filterExpression = Expression.builder()
            .expression("category = :category")
            .expressionValues(expressionValues)
            .build();

        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
            .filterExpression(filterExpression)
            .build();

        List<Product> filteredProducts = productTable.scan(scanRequest).items().stream()
            .collect(Collectors.toList());

        return buildResponse(filteredProducts, page, size);
    }

    public Product createProduct(String name, String description, Double price, String category, String userId) {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setStockQuantity(0);
        product.setStatus("ACTIVE");
        product.setCreatedBy(userId);

        productTable.putItem(product);
        return product;
    }

    private ProductListResponse buildResponse(List<Product> products, int page, int size) {
        long totalItems = products.size();
        int start = page * size;
        int end = Math.min(start + size, products.size());

        List<ProductListResponse.ProductItem> items = products.subList(start, end).stream()
            .map(p -> new ProductListResponse.ProductItem(
                p.getProductId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getCategory()
            ))
            .collect(Collectors.toList());

        return new ProductListResponse(items, page, size, totalItems);
    }
}
