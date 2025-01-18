package com.fintech.notification.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  //private String recipient;
  private String message;
  private LocalDateTime timestamp;
  private Boolean isRead ;


  @PrePersist
  public void prePersist() {
    if (timestamp == null) {
      this.timestamp = LocalDateTime.now();
    }
    if (isRead == null) {
      isRead = false;
    }
  }

    // Getters and Setters
}
