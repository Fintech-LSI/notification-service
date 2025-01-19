package com.fintech.notification.service;

import com.fintech.notification.repository.NotificationRepository;
import com.fintech.notification.dto.NotificationResponse;
import com.fintech.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;
  @Autowired
  private UserServiceClient userServiceClient;

  public Notification save(Notification notification) {
    return notificationRepository.save(notification);
  }

  public List<NotificationResponse> getAllResponses() {
    return notificationRepository.findAll().stream()
      .map(notification -> {
        NotificationResponse response = NotificationResponse.fromEntity(notification);
        String userName = userServiceClient.getUserNameById(notification.getUserId());
        return response;
      })
      .collect(Collectors.toList());
  }

  public List<NotificationResponse> getNotificationsByUserId(Long userId) {
    return notificationRepository.findByUserId(userId).stream()
      .map(notification -> {
        NotificationResponse response = NotificationResponse.fromEntity(notification);
        String userName = userServiceClient.getUserNameById(userId);
        return response;
      })
      .collect(Collectors.toList());
  }

  public List<NotificationResponse> getUnreadNotificationsByUserId(Long userId) {
    return notificationRepository.findByUserIdAndIsRead(userId, false).stream()
      .map(NotificationResponse::fromEntity)
      .collect(Collectors.toList());
  }

  public Notification markAsRead(Long notificationId) {
    Notification notification = notificationRepository.findById(notificationId)
      .orElseThrow(() -> new RuntimeException("Notification not found"));
    notification.setIsRead(true);
    return notificationRepository.save(notification);
  }

  public void deleteNotification(Long notificationId) {
    notificationRepository.deleteById(notificationId);
  }
}
