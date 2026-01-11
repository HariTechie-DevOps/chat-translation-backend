package com.example.chatapp.controller;

import com.example.chatapp.entity.User;
import com.example.chatapp.entity.Message;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final UserRepository userRepo;
    private final MessageRepository messageRepo;

    public ChatController(UserRepository userRepo, MessageRepository messageRepo) {
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
    }

    // --- USER ENDPOINTS ---

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // --- MESSAGE ENDPOINTS ---

    @PostMapping("/messages")
    public Message sendMessage(@RequestBody Message message) {
        // In a later step, we will add translation logic here
        return messageRepo.save(message);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }
}
