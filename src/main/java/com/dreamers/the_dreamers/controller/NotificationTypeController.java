package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.NotificationType;
import com.dreamers.the_dreamers.service.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationTypeController {
    
    private final NotificationTypeService notificationTypeService;
    
    @GetMapping
    public ResponseEntity<List<NotificationType>> getAllNotificationTypes() {
        return ResponseEntity.ok(notificationTypeService.getAllNotificationTypes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NotificationType> getNotificationTypeById(@PathVariable String id) {
        return notificationTypeService.getNotificationTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{typeName}")
    public ResponseEntity<NotificationType> getNotificationTypeByTypeName(@PathVariable String typeName) {
        return notificationTypeService.getNotificationTypeByTypeName(typeName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<NotificationType> createNotificationType(@RequestBody NotificationType notificationType) {
        return ResponseEntity.ok(notificationTypeService.createNotificationType(notificationType));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<NotificationType> updateNotificationType(@PathVariable String id, @RequestBody NotificationType notificationTypeDetails) {
        try {
            NotificationType updatedNotificationType = notificationTypeService.updateNotificationType(id, notificationTypeDetails);
            return ResponseEntity.ok(updatedNotificationType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationType(@PathVariable String id) {
        notificationTypeService.deleteNotificationType(id);
        return ResponseEntity.noContent().build();
    }
}
