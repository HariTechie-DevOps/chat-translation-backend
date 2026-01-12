package com.example.chatapp.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageResponse {
    private Long senderId;
    private String content;           // The main text to show
    private String originalMessage;   // Always the raw text
    private String translatedMessage; // The translated version
    private LocalDateTime timestamp;
}
