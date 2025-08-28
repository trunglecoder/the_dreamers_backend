package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.PostType;
import com.dreamers.the_dreamers.service.PostTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostTypeController {
    
    private final PostTypeService postTypeService;
    
    @GetMapping
    public ResponseEntity<List<PostType>> getAllPostTypes() {
        return ResponseEntity.ok(postTypeService.getAllPostTypes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PostType> getPostTypeById(@PathVariable String id) {
        return postTypeService.getPostTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{typeName}")
    public ResponseEntity<PostType> getPostTypeByTypeName(@PathVariable String typeName) {
        return postTypeService.getPostTypeByTypeName(typeName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<PostType> createPostType(@RequestBody PostType postType) {
        return ResponseEntity.ok(postTypeService.createPostType(postType));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PostType> updatePostType(@PathVariable String id, @RequestBody PostType postTypeDetails) {
        try {
            PostType updatedPostType = postTypeService.updatePostType(id, postTypeDetails);
            return ResponseEntity.ok(updatedPostType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostType(@PathVariable String id) {
        postTypeService.deletePostType(id);
        return ResponseEntity.noContent().build();
    }
}
