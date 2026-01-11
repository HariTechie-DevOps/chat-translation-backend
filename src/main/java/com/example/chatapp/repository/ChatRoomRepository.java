package com.example.chatapp.repository;

import com.example.chatapp.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    
    @Query("SELECT c FROM ChatRoom c WHERE (c.user1Id = :u1 AND c.user2Id = :u2) OR (c.user1Id = :u2 AND c.user2Id = :u1)")
    Optional<ChatRoom> findChatRoom(@Param("u1") Long u1, @Param("u2") Long u2);
}
