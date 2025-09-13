package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Event;
import com.dreamers.the_dreamers.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    
    private final EventRepository eventRepository;
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }
    
    public Optional<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }
    
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStartDateAfter(LocalDateTime.now());
    }
    
    public Page<Event> getUpcomingEvents(Pageable pageable) {
        return eventRepository.findByStartDateAfter(LocalDateTime.now(), pageable);
    }
    
    public List<Event> getEventsBetween(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findByStartDateBetween(start, end);
    }
    
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    
    public Event updateEvent(String id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setLocation(eventDetails.getLocation());
        
        return eventRepository.save(event);
    }
    
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}
