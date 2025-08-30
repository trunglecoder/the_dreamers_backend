package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Post;
import com.dreamers.the_dreamers.model.PostType;
import com.dreamers.the_dreamers.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;
    
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
    
    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }
    
    public List<Post> getPostsByAuthorId(String authorId) {
        return postRepository.findByAuthorId(authorId);
    }
    
    public Page<Post> getPostsByAuthorId(String authorId, Pageable pageable) {
        return postRepository.findByAuthorId(authorId, pageable);
    }
    
    public Page<Post> getPostsByType(PostType postType, Pageable pageable) {
        return postRepository.findByPostType(postType, pageable);
    }
    
    public Post createPost(Post post) {
        return postRepository.save(post);
    }
    
    public Post updatePost(String id, Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
        post.setTitle(postDetails.getTitle());
        post.setDescription(postDetails.getDescription());
        post.setImageUrl(postDetails.getImageUrl());
        post.setSocialMediaTitle(postDetails.getSocialMediaTitle());
        post.setSocialMediaContent(postDetails.getSocialMediaContent());
        post.setSocialMediaImageUrl(postDetails.getSocialMediaImageUrl());
        post.setLastSocialMediaPublishDate(postDetails.getLastSocialMediaPublishDate());
        post.setPostType(postDetails.getPostType());
        
        return postRepository.save(post);
    }
    
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}
