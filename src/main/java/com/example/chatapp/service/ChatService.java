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

        // Logic: Translation only happens from sender language -> receiver language
        String translated = translationService.translate(text, sender.getLanguage(), receiver.getLanguage());

        if (!forReceiver) {
            // Save to database only once (when processing for the sender side)
            Message message = new Message();
            message.setChatRoomId(room.getId());
            message.setSenderId(senderId);
            message.setOriginalMessage(text);
            message.setTranslatedMessage(translated);
            message.setTimestamp(timestamp != null ? timestamp : LocalDateTime.now());
            messageRepo.save(message);

            // SENDER SIDE: Content is the original text
            return new ChatMessageResponse(senderId, text, text, null, timestamp);
        } else {
            // RECEIVER SIDE: Content is original + translation
            // Similar to how WhatsApp "Translate" features look
            return new ChatMessageResponse(senderId, translated, text, translated, timestamp);
        }
    }
}
