package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.InternalDocument;
import com.dreamers.the_dreamers.repository.InternalDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InternalDocumentService {
    
    private final InternalDocumentRepository internalDocumentRepository;
    
    public List<InternalDocument> getAllInternalDocuments() {
        return internalDocumentRepository.findAll();
    }
    
    public Optional<InternalDocument> getInternalDocumentById(String id) {
        return internalDocumentRepository.findById(id);
    }
    
    public List<InternalDocument> getInternalDocumentsByUploaderId(String uploaderId) {
        return internalDocumentRepository.findByUploaderId(uploaderId);
    }
    
    public InternalDocument createInternalDocument(InternalDocument internalDocument) {
        return internalDocumentRepository.save(internalDocument);
    }
    
    public InternalDocument updateInternalDocument(String id, InternalDocument internalDocumentDetails) {
        InternalDocument internalDocument = internalDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("InternalDocument not found"));
        
        internalDocument.setTitle(internalDocumentDetails.getTitle());
        internalDocument.setFileUrl(internalDocumentDetails.getFileUrl());
        
        return internalDocumentRepository.save(internalDocument);
    }
    
    public void deleteInternalDocument(String id) {
        internalDocumentRepository.deleteById(id);
    }
}
