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

        ChatRoom room = chatRoomRepo.findChatRoom(senderId, receiverId)
                .orElseGet(() -> chatRoomRepo.save(new ChatRoom(null, senderId, receiverId)));

        // We calculate the translation first so we can save it to the DB
        String translated = translationService.translate(text, sender.getLanguage(), receiver.getLanguage());

        if (!forReceiver) {
            // Using Setters avoids constructor argument length errors!
            Message message = new Message();
            message.setChatRoomId(room.getId()); // Use the ID from the 'room' object we found/created
            message.setSenderId(senderId);
            message.setOriginalMessage(text);
            message.setTranslatedMessage(translated); // Save the translation too!
            message.setTimestamp(timestamp != null ? timestamp : LocalDateTime.now());
            
            messageRepo.save(message);
        }

        return new ChatMessageResponse(senderId, text, translated, timestamp);
    }
}
