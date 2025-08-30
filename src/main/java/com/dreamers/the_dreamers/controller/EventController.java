package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Event;
import com.dreamers.the_dreamers.service.EventService;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Event API", description = "API for managing events")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Get all events",
            description = "Returns a list of all events.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Event.class)))
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @Operation(summary = "Get events with pagination",
            description = "Returns a paginated list of events.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/page")
    public ResponseEntity<Page<Event>> getAllEventsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of events per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    @Operation(summary = "Get event by ID",
            description = "Returns a single event based on its ID.")
    @ApiResponse(responseCode = "200", description = "Event found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Event.class)))
    @ApiResponse(responseCode = "404", description = "Event not found")
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(
            @Parameter(description = "ID of the event to retrieve")
            @PathVariable String id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all upcoming events",
            description = "Returns a list of all events scheduled in the future.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Event.class)))
    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    @Operation(summary = "Get paginated upcoming events",
            description = "Returns a paginated list of all upcoming events.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/upcoming/page")
    public ResponseEntity<Page<Event>> getUpcomingEventsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of events per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.getUpcomingEvents(pageable));
    }

    @Operation(summary = "Get events between two dates",
            description = "Returns a list of events that fall within a specified date range.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Event.class)))
    @GetMapping("/between")
    public ResponseEntity<List<Event>> getEventsBetween(
            @Parameter(description = "Start date and time of the range")
            @RequestParam LocalDateTime start,
            @Parameter(description = "End date and time of the range")
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(eventService.getEventsBetween(start, end));
    }

    @Operation(summary = "Create a new event",
            description = "Creates a new event record.")
    @ApiResponse(responseCode = "200", description = "Event created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Event.class)))
    @PostMapping
    public ResponseEntity<Event> createEvent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new event", required = true)
            @RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @Operation(summary = "Update an event",
            description = "Updates an existing event based on its ID.")
    @ApiResponse(responseCode = "200", description = "Event updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Event.class)))
    @ApiResponse(responseCode = "404", description = "Event not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @Parameter(description = "ID of the event to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated event details", required = true)
            @RequestBody Event eventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDetails);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an event",
            description = "Deletes an event record based on its ID.")
    @ApiResponse(responseCode = "204", description = "Event deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @Parameter(description = "ID of the event to delete")
            @PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}