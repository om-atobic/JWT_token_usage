package com.example.JWT.config;

import com.example.JWT.model.Order;
import com.example.JWT.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.AP_SOUTH_1) // <-- use our actual region in which the table is created
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }


    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();
    }

    @Bean
    public DynamoDbTable<Product> productTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table("Products", TableSchema.fromBean(Product.class));
    }

    @Bean
    public DynamoDbTable<Order> orderTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table("Orders", TableSchema.fromBean(Order.class));
    }
}
