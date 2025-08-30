package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Notification;
import com.dreamers.the_dreamers.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Notification API", description = "API for managing user notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Get all notifications",
            description = "Returns a list of all notifications.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Notification.class)))
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @Operation(summary = "Get notifications with pagination",
            description = "Returns a paginated list of all notifications.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/page")
    public ResponseEntity<Page<Notification>> getAllNotificationsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of notifications per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(notificationService.getAllNotifications(pageable));
    }

    @Operation(summary = "Get notification by ID",
            description = "Returns a single notification based on its ID.")
    @ApiResponse(responseCode = "200", description = "Notification found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Notification.class)))
    @ApiResponse(responseCode = "404", description = "Notification not found")
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(
            @Parameter(description = "ID of the notification to retrieve")
            @PathVariable String id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get notifications for a recipient",
            description = "Returns a list of all notifications for a specific recipient user.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Notification.class)))
    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<Notification>> getNotificationsByRecipient(
            @Parameter(description = "ID of the recipient user")
            @PathVariable String recipientId) {
        return ResponseEntity.ok(notificationService.getNotificationsByRecipientId(recipientId));
    }

    @Operation(summary = "Get paginated notifications for a recipient",
            description = "Returns a paginated list of all notifications for a specific recipient user.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/recipient/{recipientId}/page")
    public ResponseEntity<Page<Notification>> getNotificationsByRecipientPaginated(
            @Parameter(description = "ID of the recipient user")
            @PathVariable String recipientId,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of notifications per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(notificationService.getNotificationsByRecipientId(recipientId, pageable));
    }

    @Operation(summary = "Get unread notifications for a recipient",
            description = "Returns a list of all unread notifications for a specific recipient user.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Notification.class)))
    @GetMapping("/recipient/{recipientId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotificationsByRecipient(
            @Parameter(description = "ID of the recipient user")
            @PathVariable String recipientId) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationsByRecipientId(recipientId));
    }

    @Operation(summary = "Create a new notification",
            description = "Creates a new notification record.")
    @ApiResponse(responseCode = "200", description = "Notification created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Notification.class)))
    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new notification", required = true)
            @RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @Operation(summary = "Update a notification",
            description = "Updates an existing notification based on its ID.")
    @ApiResponse(responseCode = "200", description = "Notification updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Notification.class)))
    @ApiResponse(responseCode = "404", description = "Notification not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @Parameter(description = "ID of the notification to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated notification details", required = true)
            @RequestBody Notification notificationDetails) {
        try {
            Notification updatedNotification = notificationService.updateNotification(id, notificationDetails);
            return ResponseEntity.ok(updatedNotification);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a notification",
            description = "Deletes a notification based on its ID.")
    @ApiResponse(responseCode = "204", description = "Notification deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @Parameter(description = "ID of the notification to delete")
            @PathVariable String id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}