package com.fintech.notification.dto;

import com.fintech.notification.model.Notification;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationRequest {
    private Long userId;
    private String recipient;
    private String message;
    //private LocalDateTime timestamp;

    public Notification toEntity() {
        Notification notification = new Notification();
        notification.setUserId(this.userId);
        notification.setMessage(this.message);
        //notification.setTimestamp(this.timestamp);
        return notification;
    }
}
