package com.fintech.notification.dto;

import com.fintech.notification.model.Notification;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class NotificationResponse {
    private Long id;
    private Long userId;
    private String recipient; // This will store the user name
    private String message;
    private LocalDateTime timestamp;

    public static NotificationResponse fromEntity(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setUserId(notification.getUserId());
        response.setRecipient(notification.getRecipient());  // Ensure this is properly mapped
        response.setMessage(notification.getMessage());      // Ensure this is properly mapped
        response.setTimestamp(notification.getTimestamp());  // Ensure this is properly mapped
        return response;
    }

}
