package com.example.chatapp.controller;

import com.example.chatapp.entity.User;
import com.example.chatapp.entity.Message;
import com.example.chatapp.dto.ChatMessageRequest;
import com.example.chatapp.dto.ChatMessageResponse;
import com.example.chatapp.service.ChatService;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*; // Added for Map, HashMap, and ArrayList

@RestController
@RequestMapping("/api")
public class ChatController {

    private final UserRepository userRepo;
    private final MessageRepository messageRepo;
    private final ChatService chatService;

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
        return chatService.processMessage(
                request.getSenderId(), 
                request.getReceiverId(), 
                request.getMessage(), 
                true, 
                java.time.LocalDateTime.now()
        );
    }

    // --- THE CLEAN VIEW LOGIC ---
    @GetMapping("/messages/view/{viewerId}")
    public List<Map<String, Object>> getChatForUser(@PathVariable Long viewerId) {
        // Using messageRepo (matching your constructor variable name)
        List<Message> allMessages = messageRepo.findAll(); 
        List<Map<String, Object>> customView = new ArrayList<>();

        for (Message msg : allMessages) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("senderId", msg.getSenderId());

            if (msg.getSenderId().equals(viewerId)) {
                // SENDER VIEW: Show exactly what I typed
                entry.put("displayText", msg.getOriginalMessage());
                entry.put("role", "SENDER");
            } else {
                // RECEIVER VIEW: Show the translation for me
                entry.put("displayText", msg.getTranslatedMessage());
                entry.put("role", "RECEIVER");
            }
            customView.add(entry);
        }
        return customView;
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }
}
