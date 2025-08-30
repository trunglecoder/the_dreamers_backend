package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.PostType;
import com.dreamers.the_dreamers.service.PostTypeService;
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
@RequestMapping("/api/post-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Post Type API", description = "API for managing post types")
public class PostTypeController {

    private final PostTypeService postTypeService;

    @Operation(summary = "Get all post types",
            description = "Returns a list of all defined post types.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostType.class)))
    @GetMapping
    public ResponseEntity<List<PostType>> getAllPostTypes() {
        return ResponseEntity.ok(postTypeService.getAllPostTypes());
    }

    @Operation(summary = "Get post type by ID",
            description = "Returns a single post type based on its ID.")
    @ApiResponse(responseCode = "200", description = "Type found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found")
    @GetMapping("/{id}")
    public ResponseEntity<PostType> getPostTypeById(
            @Parameter(description = "ID of the post type to retrieve")
            @PathVariable String id) {
        return postTypeService.getPostTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get post type by name",
            description = "Returns a single post type based on its name.")
    @ApiResponse(responseCode = "200", description = "Type found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found")
    @GetMapping("/name/{typeName}")
    public ResponseEntity<PostType> getPostTypeByTypeName(
            @Parameter(description = "Name of the post type", example = "NEWS")
            @PathVariable String typeName) {
        return postTypeService.getPostTypeByTypeName(typeName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new post type",
            description = "Creates a new post type.")
    @ApiResponse(responseCode = "200", description = "Type created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostType.class)))
    @PostMapping
    public ResponseEntity<PostType> createPostType(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new post type", required = true)
            @RequestBody PostType postType) {
        return ResponseEntity.ok(postTypeService.createPostType(postType));
    }

    @Operation(summary = "Update a post type",
            description = "Updates an existing post type based on its ID.")
    @ApiResponse(responseCode = "200", description = "Type updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<PostType> updatePostType(
            @Parameter(description = "ID of the post type to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated post type details", required = true)
            @RequestBody PostType postTypeDetails) {
        try {
            PostType updatedPostType = postTypeService.updatePostType(id, postTypeDetails);
            return ResponseEntity.ok(updatedPostType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a post type",
            description = "Deletes a post type based on its ID.")
    @ApiResponse(responseCode = "204", description = "Type deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostType(
            @Parameter(description = "ID of the post type to delete")
            @PathVariable String id) {
        postTypeService.deletePostType(id);
        return ResponseEntity.noContent().build();
    }
}