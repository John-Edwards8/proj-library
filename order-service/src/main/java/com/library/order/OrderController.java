package com.library.order;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/{userId}")
    public List<Object> getOrders(@PathVariable String userId) {
        return orderService.getOrders(userId);
    }
}
