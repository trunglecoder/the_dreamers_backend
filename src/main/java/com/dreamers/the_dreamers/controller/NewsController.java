package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.News;
import com.dreamers.the_dreamers.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NewsController {
    
    private final NewsService newsService;
    
    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }
    
    @GetMapping("/page")
    public ResponseEntity<Page<News>> getAllNewsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.getAllNews(pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable String id) {
        return newsService.getNewsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<News>> getRecentNews() {
        return ResponseEntity.ok(newsService.getRecentNews());
    }
    
    @GetMapping("/recent/page")
    public ResponseEntity<Page<News>> getRecentNewsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.getRecentNews(pageable));
    }
    
    @GetMapping("/between")
    public ResponseEntity<List<News>> getNewsBetween(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(newsService.getNewsBetween(start, end));
    }
    
    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody News news) {
        return ResponseEntity.ok(newsService.createNews(news));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable String id, @RequestBody News newsDetails) {
        try {
            News updatedNews = newsService.updateNews(id, newsDetails);
            return ResponseEntity.ok(updatedNews);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable String id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}
