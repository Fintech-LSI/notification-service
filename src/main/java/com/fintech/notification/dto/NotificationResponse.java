package com.fintech.notification.dto;

import com.fintech.notification.model.Notification;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private Long userId;
    private String recipient;
    private String message;
    private LocalDateTime timestamp;

    public static NotificationResponse fromEntity(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setUserId(notification.getUserId());
        response.setRecipient(notification.getRecipient());
        response.setMessage(notification.getMessage());
        response.setTimestamp(notification.getTimestamp());
        return response;
    }
}