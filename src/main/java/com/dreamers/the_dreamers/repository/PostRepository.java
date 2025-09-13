package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.Post;
import com.dreamers.the_dreamers.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Page<Post> findByPostType(PostType postType, Pageable pageable);
    List<Post> findByAuthorId(Long authorId);
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
}
