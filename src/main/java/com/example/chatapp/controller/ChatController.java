package com.example.chatapp.controller;

import com.example.chatapp.entity.User;
import com.example.chatapp.entity.Message;
import com.example.chatapp.dto.ChatMessageRequest;   // Added Import
import com.example.chatapp.dto.ChatMessageResponse;  // Added Import
import com.example.chatapp.service.ChatService;      // Added Import
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final UserRepository userRepo;
    private final MessageRepository messageRepo;
    private final ChatService chatService; // Inject ChatService

    public ChatController(UserRepository userRepo, MessageRepository messageRepo, ChatService chatService) {
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
        this.chatService = chatService;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PostMapping("/messages")
    public ChatMessageResponse sendMessage(@RequestBody ChatMessageRequest request) {
        // Use the service logic so translation actually happens
        return chatService.processMessage(
                request.getSenderId(), 
                request.getReceiverId(), 
                request.getMessage(), 
                true, 
                java.time.LocalDateTime.now()
        );
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }
}
