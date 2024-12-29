package com.fintech.notification.service;

import com.fintech.notification.dto.NotificationRepository;
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
                    System.out.println(response);
                    String userName = userServiceClient.getUserNameById(notification.getUserId());

                    response.setRecipient(userName); // Replace recipient with user name
                    return response;
                })
                .collect(Collectors.toList());
    }

    // New method to fetch notifications by user ID
    public List<NotificationResponse> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(notification -> {
                    NotificationResponse response = NotificationResponse.fromEntity(notification);
                    String userName = userServiceClient.getUserNameById(userId);
                    response.setRecipient(userName); // Set recipient as the user name
                    return response;
                })
                .collect(Collectors.toList());
    }
}