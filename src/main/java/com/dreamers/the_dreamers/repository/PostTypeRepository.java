package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType, String> {
    Optional<PostType> findByTypeName(String typeName);
}
