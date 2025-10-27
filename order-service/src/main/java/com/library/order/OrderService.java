package com.library.order;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final AtomicInteger counter = new AtomicInteger(1);

    public Map<String, Object> createOrder(Map<String, Object> orderData) {
        int id = counter.getAndIncrement();
        orderData.put("id", id);

        redisTemplate.opsForHash().putAll("order:" + id, orderData);
        redisTemplate.convertAndSend("order_created", orderData);

        return Map.of("status", "created", "orderId", id);
    }

    public List<Object> getOrders(String userId) {
        return List.of(redisTemplate.opsForHash().entries("order:" + userId));
    }
}
