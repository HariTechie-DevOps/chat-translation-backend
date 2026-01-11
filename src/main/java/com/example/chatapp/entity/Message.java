package com.example.chatapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatRoomId;
    private Long senderId;

    @Column(length = 2000)
    private String originalMessage;

    // ADD THIS: To store the translated text
    @Column(length = 2000)
    private String translatedMessage;

    private LocalDateTime timestamp;

    // ADD THIS: This automatically sets the time so you don't get "null"
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
