package com.library.order;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> orderData) {
        return orderService.createOrder(orderData);
    }

    @PutMapping("/{orderId}/status")
    public Map<String, Object> updateOrderStatus(@PathVariable Long orderId,
                                                 @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        orderService.updateOrderStatus(orderId, newStatus);
        return Map.of("status", "updated", "orderId", orderId, "newStatus", newStatus);
    }

    @GetMapping("/user/{userId}")
    public List<Map<Object, Object>> getOrdersByUser(@PathVariable String userId) {
        return orderService.getOrders(userId);
    }

    @GetMapping("/{orderId}/history")
    public List<String> getOrderHistory(@PathVariable Long orderId) {
        return orderService.getOrderHistory(orderId);
    }
}
