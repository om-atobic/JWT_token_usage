package com.example.JWT.dao;


import com.example.JWT.model.Order;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDao {
    private final DynamoDbTable<Order> orderTable;
    public OrderDao(DynamoDbTable<Order> orderTable) {
        this.orderTable = orderTable;
    }
    public void save(Order order) {
        orderTable.putItem(order);
    }
    public void update(Order order) {
        orderTable.updateItem(order);
    }

    public Order getByOrderId(String orderId) {

        QueryConditional condition =
                QueryConditional.keyEqualTo(
                        Key.builder()
                                .partitionValue(orderId)
                                .build()
                );

        return orderTable.query(condition)
                .items()
                .stream()
                .findFirst()
                .orElse(null);
       // return orderTable.getItem(
        //        Key.builder().partitionValue(orderId).build());
    }
    public List<Order> getByUserId(String userId) {
        DynamoDbIndex<Order> index =
                orderTable.index("userId-createdAt-index");

        QueryConditional conditional = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(userId).build()
        );

        List<Order> orders = new ArrayList<>();
        index.query(conditional).forEach(p -> orders.addAll(p.items()));
        return orders;
    }
    public List<Order> getByUserIdAndStatus(String userId, String status) {
        DynamoDbIndex<Order> index =
                orderTable.index("userId-createdAt-index");

        QueryConditional qc = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(userId).build()
        );

        Expression filter = Expression.builder()
                .expression("#status = :status")
                .expressionNames(Map.of("#status", "status"))
                .expressionValues(
                        Map.of(":status",
                                AttributeValue.builder().s(status).build())
                )
                .build();

        List<Order> orders = new ArrayList<>();

        index.query(r -> r
                .queryConditional(qc)
                .filterExpression(filter)
        ).forEach(p -> orders.addAll(p.items()));

        return orders;
    }
}
