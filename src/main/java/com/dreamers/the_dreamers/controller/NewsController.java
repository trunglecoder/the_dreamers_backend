package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.News;
import com.dreamers.the_dreamers.service.NewsService;
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
@RequestMapping("/api/news")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "News API", description = "API for managing news articles")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "Get all news articles",
            description = "Returns a list of all news articles.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class)))
    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @Operation(summary = "Get news with pagination",
            description = "Returns a paginated list of news articles.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/page")
    public ResponseEntity<Page<News>> getAllNewsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of news articles per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.getAllNews(pageable));
    }

    @Operation(summary = "Get news article by ID",
            description = "Returns a single news article based on its ID.")
    @ApiResponse(responseCode = "200", description = "News article found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class)))
    @ApiResponse(responseCode = "404", description = "News article not found")
    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(
            @Parameter(description = "ID of the news article to retrieve")
            @PathVariable String id) {
        return newsService.getNewsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get recent news",
            description = "Returns a list of the most recent news articles.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class)))
    @GetMapping("/recent")
    public ResponseEntity<List<News>> getRecentNews() {
        return ResponseEntity.ok(newsService.getRecentNews());
    }

    @Operation(summary = "Get recent news with pagination",
            description = "Returns a paginated list of the most recent news articles.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/recent/page")
    public ResponseEntity<Page<News>> getRecentNewsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of news articles per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.getRecentNews(pageable));
    }

    @Operation(summary = "Get news articles between two dates",
            description = "Returns a list of news articles published within a specified date range.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class)))
    @GetMapping("/between")
    public ResponseEntity<List<News>> getNewsBetween(
            @Parameter(description = "Start date and time of the range")
            @RequestParam LocalDateTime start,
            @Parameter(description = "End date and time of the range")
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(newsService.getNewsBetween(start, end));
    }

    @Operation(summary = "Create a new news article",
            description = "Creates a new news article record.")
    @ApiResponse(responseCode = "200", description = "News article created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class)))
    @PostMapping
    public ResponseEntity<News> createNews(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new news article", required = true)
            @RequestBody News news) {
        return ResponseEntity.ok(newsService.createNews(news));
    }

    @Operation(summary = "Update a news article",
            description = "Updates an existing news article based on its ID.")
    @ApiResponse(responseCode = "200", description = "News article updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class)))
    @ApiResponse(responseCode = "404", description = "News article not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(
            @Parameter(description = "ID of the news article to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated news article details", required = true)
            @RequestBody News newsDetails) {
        try {
            News updatedNews = newsService.updateNews(id, newsDetails);
            return ResponseEntity.ok(updatedNews);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a news article",
            description = "Deletes a news article record based on its ID.")
    @ApiResponse(responseCode = "204", description = "News article deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(
            @Parameter(description = "ID of the news article to delete")
            @PathVariable String id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}