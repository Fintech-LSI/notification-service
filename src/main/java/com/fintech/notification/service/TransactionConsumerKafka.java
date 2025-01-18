package com.fintech.notification.service;

import com.fintech.notification.dto.NotificationRequest;
import com.fintech.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumerKafka {
  @Autowired
  private NotificationService notificationService;

  @KafkaListener(
    topics = "notification-requests",
    groupId = "notification-service-group"
  )
  public void consumeTransaction(NotificationRequest request) {
    System.out.println("Received notification request: " + request);
    Notification notification = new Notification();
    notification.setUserId(request.getUserId());
    notification.setMessage(request.getMessage());
    notification.setRecipient(request.getRecipient());
    notificationService.save(notification);
  }
}
