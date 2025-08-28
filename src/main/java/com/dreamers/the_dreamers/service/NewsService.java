package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.News;
import com.dreamers.the_dreamers.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {
    
    private final NewsRepository newsRepository;
    
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }
    
    public Page<News> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }
    
    public Optional<News> getNewsById(String id) {
        return newsRepository.findById(id);
    }
    
    public List<News> getRecentNews() {
        return newsRepository.findByPublishedDateAfter(LocalDateTime.now().minusDays(30));
    }
    
    public Page<News> getRecentNews(Pageable pageable) {
        return newsRepository.findByPublishedDateAfter(LocalDateTime.now().minusDays(30), pageable);
    }
    
    public List<News> getNewsBetween(LocalDateTime start, LocalDateTime end) {
        return newsRepository.findByPublishedDateBetween(start, end);
    }
    
    public News createNews(News news) {
        return newsRepository.save(news);
    }
    
    public News updateNews(String id, News newsDetails) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
        
        news.setPublishedDate(newsDetails.getPublishedDate());
        
        return newsRepository.save(news);
    }
    
    public void deleteNews(String id) {
        newsRepository.deleteById(id);
    }
}
