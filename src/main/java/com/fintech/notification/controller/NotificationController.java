package com.fintech.notification.controller;


import com.fintech.notification.dto.NotificationRequest;
import com.fintech.notification.dto.NotificationResponse;
import com.fintech.notification.model.Notification;
import com.fintech.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public Notification createNotification(@RequestBody NotificationRequest notificationRequest) {
        return notificationService.save(notificationRequest.toEntity());
    }

    @GetMapping("/test")
    public String test() {
      return "notification is running";
    }

    @GetMapping
    public List<NotificationResponse> getAllNotifications() {
        return notificationService.getAllResponses();
    }
    // New endpoint to fetch notifications by user ID
    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getNotificationsByUserId(@PathVariable Long userId) {
        return notificationService.getNotificationsByUserId(userId);
    }
}
