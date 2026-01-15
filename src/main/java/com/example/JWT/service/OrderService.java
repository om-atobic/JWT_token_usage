package com.example.JWT.service;

import com.example.JWT.dao.OrderDao;
import com.example.JWT.dao.ProductDao;
import com.example.JWT.model.Order;
import com.example.JWT.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {
    private final OrderDao orderDao;
    private final ProductDao productDao;


    public OrderService(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    public Order createOrders(
            List<String> productIds,
            List<Integer> quantities,
            String shippingAddress,
            String paymentMethod,
            String userId
    ) {

        List<Order.OrderItem> items = new ArrayList<>();
        double totalAmount = 0.0;

        for (int i = 0; i < productIds.size(); i++) {
            Product product = productDao.getById(productIds.get(i));

            int quantity = quantities.get(i);
            double subtotal = product.getPrice() * quantity;

            items.add(new Order.OrderItem(
                    product.getProductId(),
                    product.getName(),
                    quantity,
                    product.getPrice()
            ));
            totalAmount += subtotal;
        }

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setItems(items);
        order.setStatus("CREATED");
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setCreatedAt(System.currentTimeMillis());
        order.setUpdatedAt(System.currentTimeMillis());

        orderDao.save(order);
        return order;
    }
    public List<Order> getOrdersByUserId(String userId, String status) {
        if (status == null || status.isBlank()) {
            return orderDao.getByUserId(userId);
        }
        return orderDao.getByUserIdAndStatus(userId, status);
    }
    public Order getOrderByOrderId(String orderId) {
        return orderDao.getByOrderId(orderId);
    }

    public boolean updateOrderStatus(String orderId, String userId, String status) {
        Order order = orderDao.getByOrderId(orderId);
        log.info("Order status: {}", order.getStatus());
        if (order == null) return false;
        if (!order.getUserId().equals(userId)) return false;
        if ("DELIVERED".equals(order.getStatus())) return false;

        log.info("Order status: {}", order.getStatus());

        order.setStatus(status);
        order.setUpdatedAt(System.currentTimeMillis());

        orderDao.update(order);
        return true;
    }

    public boolean cancelOrder(String orderId, String userId, String reason) {
        Order order = orderDao.getByOrderId(orderId);

        if (order == null) return false;
        if (!order.getUserId().equals(userId)) return false;
        if ("DELIVERED".equals(order.getStatus())) return false;

        order.setStatus("CANCELLED");
        order.setCancelReason(reason);
        order.setUpdatedAt(System.currentTimeMillis());

        orderDao.update(order);
        return true;
    }

}
