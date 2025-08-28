package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.NotificationType;
import com.dreamers.the_dreamers.repository.NotificationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationTypeService {
    
    private final NotificationTypeRepository notificationTypeRepository;
    
    public List<NotificationType> getAllNotificationTypes() {
        return notificationTypeRepository.findAll();
    }
    
    public Optional<NotificationType> getNotificationTypeById(String id) {
        return notificationTypeRepository.findById(id);
    }
    
    public Optional<NotificationType> getNotificationTypeByTypeName(String typeName) {
        return notificationTypeRepository.findByTypeName(typeName);
    }
    
    public NotificationType createNotificationType(NotificationType notificationType) {
        return notificationTypeRepository.save(notificationType);
    }
    
    public NotificationType updateNotificationType(String id, NotificationType notificationTypeDetails) {
        NotificationType notificationType = notificationTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationType not found"));
        
        notificationType.setTypeName(notificationTypeDetails.getTypeName());
        
        return notificationTypeRepository.save(notificationType);
    }
    
    public void deleteNotificationType(String id) {
        notificationTypeRepository.deleteById(id);
    }
}
