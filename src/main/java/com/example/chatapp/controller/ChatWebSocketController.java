package com.example.chatapp.controller;

import com.example.chatapp.dto.*;
import com.example.chatapp.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    public ChatWebSocketController(SimpMessagingTemplate m, ChatService c) {
        this.messagingTemplate = m;
        this.chatService = c;
    }

    @MessageMapping("/chat.send")
    public void send(ChatMessageRequest request) {

        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.getSenderId(),
                chatService.processMessage(
                        request.getSenderId(),
                        request.getReceiverId(),
                        request.getMessage(),
                        false)
        );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.getReceiverId(),
                chatService.processMessage(
                        request.getSenderId(),
                        request.getReceiverId(),
                        request.getMessage(),
                        true)
        );
    }
}
