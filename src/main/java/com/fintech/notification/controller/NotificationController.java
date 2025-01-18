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
    Notification notification = new Notification();
    notification.setUserId(1L);
    notification.setMessage("test");
    notificationService.save(notification);
    return "Notification service is running";
  }

  @GetMapping
  public List<NotificationResponse> getAllNotifications() {
    return notificationService.getAllResponses();
  }

  @GetMapping("/user/{userId}")
  public List<NotificationResponse> getNotificationsByUserId(@PathVariable Long userId) {
    return notificationService.getNotificationsByUserId(userId);
  }

  @GetMapping("/user/{userId}/unread")
  public List<NotificationResponse> getUnreadNotificationsByUserId(@PathVariable Long userId) {
    return notificationService.getUnreadNotificationsByUserId(userId);
  }

  @PostMapping("/{notificationId}/read")
  public Notification markAsRead(@PathVariable Long notificationId) {
    return notificationService.markAsRead(notificationId);
  }

  @DeleteMapping("/{notificationId}")
  public void deleteNotification(@PathVariable Long notificationId) {
    notificationService.deleteNotification(notificationId);
  }
}
