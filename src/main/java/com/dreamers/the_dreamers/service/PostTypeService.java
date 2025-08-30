package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.PostType;
import com.dreamers.the_dreamers.repository.PostTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostTypeService {
    
    private final PostTypeRepository postTypeRepository;
    
    public List<PostType> getAllPostTypes() {
        return postTypeRepository.findAll();
    }
    
    public Optional<PostType> getPostTypeById(String id) {
        return postTypeRepository.findById(id);
    }
    
    public Optional<PostType> getPostTypeByTypeName(String typeName) {
        return postTypeRepository.findByTypeName(typeName);
    }
    
    public PostType createPostType(PostType postType) {
        return postTypeRepository.save(postType);
    }
    
    public PostType updatePostType(String id, PostType postTypeDetails) {
        PostType postType = postTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PostType not found"));
        
        postType.setTypeName(postTypeDetails.getTypeName());
        
        return postTypeRepository.save(postType);
    }
    
    public void deletePostType(String id) {
        postTypeRepository.deleteById(id);
    }
}
