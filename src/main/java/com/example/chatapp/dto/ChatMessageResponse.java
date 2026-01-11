package com.example.chatapp.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor // Add this for JSON deserialization
@Builder           // Optional: Helpful for creating responses in your Service
public class ChatMessageResponse {
    private Long senderId;
    private String content;
    private String originalMessage;
    private String translatedMessage;
    private LocalDateTime timestamp;
}
