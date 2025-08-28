package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    List<News> findByPublishedDateAfter(LocalDateTime date);
    Page<News> findByPublishedDateAfter(LocalDateTime date, Pageable pageable);
    List<News> findByPublishedDateBetween(LocalDateTime start, LocalDateTime end);
}
