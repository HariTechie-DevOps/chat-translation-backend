package com.example.chatapp.controller;

import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class ChatController {

    private final UserRepository userRepo;

    public ChatController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }
}
