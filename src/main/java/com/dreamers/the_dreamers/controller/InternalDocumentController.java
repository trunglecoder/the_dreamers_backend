package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.InternalDocument;
import com.dreamers.the_dreamers.service.InternalDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internal-documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InternalDocumentController {
    
    private final InternalDocumentService internalDocumentService;
    
    @GetMapping
    public ResponseEntity<List<InternalDocument>> getAllInternalDocuments() {
        return ResponseEntity.ok(internalDocumentService.getAllInternalDocuments());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InternalDocument> getInternalDocumentById(@PathVariable String id) {
        return internalDocumentService.getInternalDocumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/uploader/{uploaderId}")
    public ResponseEntity<List<InternalDocument>> getInternalDocumentsByUploader(@PathVariable String uploaderId) {
        return ResponseEntity.ok(internalDocumentService.getInternalDocumentsByUploaderId(uploaderId));
    }
    
    @PostMapping
    public ResponseEntity<InternalDocument> createInternalDocument(@RequestBody InternalDocument internalDocument) {
        return ResponseEntity.ok(internalDocumentService.createInternalDocument(internalDocument));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<InternalDocument> updateInternalDocument(@PathVariable String id, @RequestBody InternalDocument internalDocumentDetails) {
        try {
            InternalDocument updatedInternalDocument = internalDocumentService.updateInternalDocument(id, internalDocumentDetails);
            return ResponseEntity.ok(updatedInternalDocument);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternalDocument(@PathVariable String id) {
        internalDocumentService.deleteInternalDocument(id);
        return ResponseEntity.noContent().build();
    }
}
