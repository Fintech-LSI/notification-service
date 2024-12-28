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

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<NotificationResponse> getAllResponses() {
        return notificationRepository.findAll().stream()
                .map(NotificationResponse::fromEntity)
                .collect(Collectors.toList());
    }
}