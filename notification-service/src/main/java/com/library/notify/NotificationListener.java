package com.library.notify;

import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationListener implements MessageListener {

    private final NotificationService notificationService;

    @Override
    public void onMessage(Message message, @SuppressWarnings("null") byte[] pattern) {
        String data = new String(message.getBody(), StandardCharsets.UTF_8);
        notificationService.sendNotification("New order: " + data);
    }
}
