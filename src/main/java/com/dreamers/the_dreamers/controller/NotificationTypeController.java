package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.NotificationType;
import com.dreamers.the_dreamers.service.NotificationTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Notification Type API", description = "API for managing notification types")
public class NotificationTypeController {

    private final NotificationTypeService notificationTypeService;

    @Operation(summary = "Get all notification types",
            description = "Returns a list of all defined notification types.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = NotificationType.class)))
    @GetMapping
    public ResponseEntity<List<NotificationType>> getAllNotificationTypes() {
        return ResponseEntity.ok(notificationTypeService.getAllNotificationTypes());
    }

    @Operation(summary = "Get notification type by ID",
            description = "Returns a single notification type based on its ID.")
    @ApiResponse(responseCode = "200", description = "Type found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = NotificationType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found")
    @GetMapping("/{id}")
    public ResponseEntity<NotificationType> getNotificationTypeById(
            @Parameter(description = "ID of the notification type to retrieve")
            @PathVariable String id) {
        return notificationTypeService.getNotificationTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get notification type by name",
            description = "Returns a single notification type based on its name.")
    @ApiResponse(responseCode = "200", description = "Type found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = NotificationType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found")
    @GetMapping("/name/{typeName}")
    public ResponseEntity<NotificationType> getNotificationTypeByTypeName(
            @Parameter(description = "Name of the notification type", example = "PROJECT_UPDATE")
            @PathVariable String typeName) {
        return notificationTypeService.getNotificationTypeByTypeName(typeName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new notification type",
            description = "Creates a new notification type.")
    @ApiResponse(responseCode = "200", description = "Type created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = NotificationType.class)))
    @PostMapping
    public ResponseEntity<NotificationType> createNotificationType(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new notification type", required = true)
            @RequestBody NotificationType notificationType) {
        return ResponseEntity.ok(notificationTypeService.createNotificationType(notificationType));
    }

    @Operation(summary = "Update a notification type",
            description = "Updates an existing notification type based on its ID.")
    @ApiResponse(responseCode = "200", description = "Type updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = NotificationType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<NotificationType> updateNotificationType(
            @Parameter(description = "ID of the notification type to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated notification type details", required = true)
            @RequestBody NotificationType notificationTypeDetails) {
        try {
            NotificationType updatedNotificationType = notificationTypeService.updateNotificationType(id, notificationTypeDetails);
            return ResponseEntity.ok(updatedNotificationType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a notification type",
            description = "Deletes a notification type based on its ID.")
    @ApiResponse(responseCode = "204", description = "Type deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationType(
            @Parameter(description = "ID of the notification type to delete")
            @PathVariable String id) {
        notificationTypeService.deleteNotificationType(id);
        return ResponseEntity.noContent().build();
    }
}