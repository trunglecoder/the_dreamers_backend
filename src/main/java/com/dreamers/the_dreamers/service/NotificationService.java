package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Notification;
import com.dreamers.the_dreamers.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    
    public Page<Notification> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }
    
    public Optional<Notification> getNotificationById(String id) {
        return notificationRepository.findById(id);
    }
    
    public List<Notification> getNotificationsByRecipientId(String recipientId) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(recipientId);
    }
    
    public Page<Notification> getNotificationsByRecipientId(String recipientId, Pageable pageable) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(recipientId, pageable);
    }
    
    public List<Notification> getUnreadNotificationsByRecipientId(String recipientId) {
        return notificationRepository.findByRecipientIdAndIsReadFalse(recipientId);
    }
    
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
    
    public Notification updateNotification(String id, Notification notificationDetails) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        notification.setTitle(notificationDetails.getTitle());
        notification.setContent(notificationDetails.getContent());
        notification.setIsRead(notificationDetails.getIsRead());
        notification.setLink(notificationDetails.getLink());
        notification.setNotificationType(notificationDetails.getNotificationType());
        
        return notificationRepository.save(notification);
    }
    
    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }
}
