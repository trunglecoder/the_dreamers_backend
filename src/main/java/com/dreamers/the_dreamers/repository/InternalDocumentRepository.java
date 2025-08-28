package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.InternalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternalDocumentRepository extends JpaRepository<InternalDocument, String> {
    List<InternalDocument> findByUploaderId(String uploaderId);
}
