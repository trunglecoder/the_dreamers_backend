package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Post;
import com.dreamers.the_dreamers.model.PostType;
import com.dreamers.the_dreamers.service.PostService;
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
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Post API", description = "API for managing general posts (Events, News, Projects)")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Get all posts",
            description = "Returns a list of all posts, regardless of type.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Post.class)))
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @Operation(summary = "Get posts with pagination",
            description = "Returns a paginated list of all posts.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/page")
    public ResponseEntity<Page<Post>> getAllPostsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of posts per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @Operation(summary = "Get post by ID",
            description = "Returns a single post based on its ID.")
    @ApiResponse(responseCode = "200", description = "Post found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Post.class)))
    @ApiResponse(responseCode = "404", description = "Post not found")
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(
            @Parameter(description = "ID of the post to retrieve")
            @PathVariable String id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get posts by author ID",
            description = "Returns a list of all posts created by a specific author.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Post.class)))
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Post>> getPostsByAuthor(
            @Parameter(description = "ID of the author")
            @PathVariable Long authorId) {
        return ResponseEntity.ok(postService.getPostsByAuthorId(authorId));
    }

    @Operation(summary = "Get paginated posts by author ID",
            description = "Returns a paginated list of all posts created by a specific author.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/author/{authorId}/page")
    public ResponseEntity<Page<Post>> getPostsByAuthorPaginated(
            @Parameter(description = "ID of the author")
            @PathVariable Long authorId,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of posts per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getPostsByAuthorId(authorId, pageable));
    }

    @Operation(summary = "Get posts by post type",
            description = "Returns a paginated list of posts of a specific type (e.g., Event, News, Project).")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/type/{postType}")
    public ResponseEntity<Page<Post>> getPostsByType(
            @Parameter(description = "Type of the post", example = "NEWS")
            @PathVariable PostType postType,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of posts per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getPostsByType(postType, pageable));
    }

    @Operation(summary = "Create a new post",
            description = "Creates a new post of a generic type.")
    @ApiResponse(responseCode = "200", description = "Post created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Post.class)))
    @PostMapping
    public ResponseEntity<Post> createPost(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new post", required = true)
            @RequestBody Post post) {
        return ResponseEntity.ok(postService.createPost(post));
    }

    @Operation(summary = "Update a post",
            description = "Updates an existing post based on its ID.")
    @ApiResponse(responseCode = "200", description = "Post updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Post.class)))
    @ApiResponse(responseCode = "404", description = "Post not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @Parameter(description = "ID of the post to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated post details", required = true)
            @RequestBody Post postDetails) {
        try {
            Post updatedPost = postService.updatePost(id, postDetails);
            return ResponseEntity.ok(updatedPost);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a post",
            description = "Deletes a post based on its ID.")
    @ApiResponse(responseCode = "204", description = "Post deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "ID of the post to delete")
            @PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}