package com.example.chatapp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class ChatMessageResponse {
    private Long senderId;
    private String originalMessage;
    private String translatedMessage;
    private LocalDateTime timestamp;
}
