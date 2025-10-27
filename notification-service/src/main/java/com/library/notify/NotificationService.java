package com.library.notify;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String message) {
        System.out.println("[NOTIFICATION SERVICE] " + message);
    }
}