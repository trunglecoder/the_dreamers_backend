package com.dreamers.the_dreamers.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "chatbot_history")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
public class ChatbotHistory extends BaseEntity {
    
    @Column(name = "session_id", length = 255)
    private String sessionId;
    
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "is_user_message", nullable = false)
    private Boolean isUserMessage;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-chatbotHistory")
    private User user;
}
