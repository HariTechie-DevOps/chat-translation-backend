package com.example.chatapp.service;

import com.example.chatapp.dto.ChatMessageResponse;
import com.example.chatapp.entity.*;
import com.example.chatapp.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatService {

    private final UserRepository userRepo;
    private final ChatRoomRepository chatRoomRepo;
    private final MessageRepository messageRepo;
    private final TranslationService translationService;

    public ChatService(UserRepository u, ChatRoomRepository c, MessageRepository m, TranslationService t) {
        this.userRepo = u;
        this.chatRoomRepo = c;
        this.messageRepo = m;
        this.translationService = t;
    }

    public ChatMessageResponse processMessage(Long senderId, Long receiverId, String text, boolean forReceiver, LocalDateTime timestamp) {
        User sender = userRepo.findById(senderId).orElseThrow();
        User receiver = userRepo.findById(receiverId).orElseThrow();

        // Use the improved repository method
        ChatRoom room = chatRoomRepo.findChatRoom(senderId, receiverId)
                .orElseGet(() -> chatRoomRepo.save(new ChatRoom(null, senderId, receiverId)));

        // logic to only save to DB if it's the FIRST call (for the sender)
        if (!forReceiver) {
            Message msg = new Message(null, room.getId(), senderId, text, timestamp);
            messageRepo.save(msg);
        }

        String translated = forReceiver
                ? translationService.translate(text, sender.getLanguage(), receiver.getLanguage())
                : null;

        return new ChatMessageResponse(senderId, text, translated, timestamp);
    }
}
