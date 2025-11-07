package com.library.order;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> createOrder(Map<String, Object> orderData) {
        Long id = redisTemplate.opsForValue().increment("order:id:counter");
        String timestamp = Instant.now().toString();
        String newStatus = "CREATED";
        orderData.put("id", id);
        orderData.put("status", newStatus);
        orderData.put("createdAt", timestamp);

        String userId = orderData.get("userId").toString();
        redisTemplate.opsForHash().putAll("order:" + userId + ":" + id, orderData);
        redisTemplate.convertAndSend("order_created", orderData);
        redisTemplate.opsForList().leftPush("order:" + id + ":history", timestamp + ":" + newStatus);

        return Map.of("status", "created", "orderId", id);
    }

    public void updateOrderStatus(Long orderId, String newStatus) {
        String timestamp = Instant.now().toString();

        redisTemplate.opsForHash().put("order:" + orderId, "status", newStatus);
        redisTemplate.opsForList().leftPush("order:" + orderId + ":history", timestamp + ":" + newStatus);
        
        Map<String, Object> event = Map.of(
                "orderId", orderId,
                "status", newStatus,
                "timestamp", timestamp
        );

        redisTemplate.convertAndSend("order_status_changed", event);
    }

    @SuppressWarnings("null")
    public List<String> getOrderHistory(Long orderId) {
        return redisTemplate.opsForList()
                .range("order:" + orderId + ":history", 0, -1)
                .stream()
                .map(Object::toString)
                .toList();
    }

    public List<Map<Object, Object>> getOrders(String userId) {        
        List<Map<Object, Object>> orders = new ArrayList<>();

        for (String orderKey : redisTemplate.keys("order:" + userId + ":*")) {
            Map<Object, Object> orderData = redisTemplate.opsForHash().entries(orderKey);
            orders.add(orderData);
        }
        
        return orders;
    }
}
