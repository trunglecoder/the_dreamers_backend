package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByStartDateAfter(LocalDateTime date);
    List<Event> findByStartDateBetween(LocalDateTime start, LocalDateTime end);
    Page<Event> findByStartDateAfter(LocalDateTime date, Pageable pageable);
}
