package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.InternalDocument;
import com.dreamers.the_dreamers.service.InternalDocumentService;
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
@RequestMapping("/api/internal-documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Internal Document API", description = "API for managing internal documents for members and admins")
public class InternalDocumentController {

    private final InternalDocumentService internalDocumentService;

    @Operation(summary = "Get all internal documents",
            description = "Returns a list of all internal documents.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = InternalDocument.class)))
    @GetMapping
    public ResponseEntity<List<InternalDocument>> getAllInternalDocuments() {
        return ResponseEntity.ok(internalDocumentService.getAllInternalDocuments());
    }

    @Operation(summary = "Get internal document by ID",
            description = "Returns a single internal document based on its ID.")
    @ApiResponse(responseCode = "200", description = "Document found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = InternalDocument.class)))
    @ApiResponse(responseCode = "404", description = "Document not found")
    @GetMapping("/{id}")
    public ResponseEntity<InternalDocument> getInternalDocumentById(
            @Parameter(description = "ID of the internal document to retrieve")
            @PathVariable String id) {
        return internalDocumentService.getInternalDocumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get internal documents by uploader ID",
            description = "Returns a list of internal documents uploaded by a specific user.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = InternalDocument.class)))
    @GetMapping("/uploader/{uploaderId}")
    public ResponseEntity<List<InternalDocument>> getInternalDocumentsByUploader(
            @Parameter(description = "ID of the uploader")
            @PathVariable Long uploaderId) {
        return ResponseEntity.ok(internalDocumentService.getInternalDocumentsByUploaderId(uploaderId));
    }

    @Operation(summary = "Create a new internal document",
            description = "Creates a new internal document record.")
    @ApiResponse(responseCode = "200", description = "Document created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = InternalDocument.class)))
    @PostMapping
    public ResponseEntity<InternalDocument> createInternalDocument(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new internal document", required = true)
            @RequestBody InternalDocument internalDocument) {
        return ResponseEntity.ok(internalDocumentService.createInternalDocument(internalDocument));
    }

    @Operation(summary = "Update an internal document",
            description = "Updates an existing internal document based on its ID.")
    @ApiResponse(responseCode = "200", description = "Document updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = InternalDocument.class)))
    @ApiResponse(responseCode = "404", description = "Document not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<InternalDocument> updateInternalDocument(
            @Parameter(description = "ID of the internal document to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated internal document details", required = true)
            @RequestBody InternalDocument internalDocumentDetails) {
        try {
            InternalDocument updatedInternalDocument = internalDocumentService.updateInternalDocument(id, internalDocumentDetails);
            return ResponseEntity.ok(updatedInternalDocument);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an internal document",
            description = "Deletes an internal document based on its ID.")
    @ApiResponse(responseCode = "204", description = "Document deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternalDocument(
            @Parameter(description = "ID of the internal document to delete")
            @PathVariable String id) {
        internalDocumentService.deleteInternalDocument(id);
        return ResponseEntity.noContent().build();
    }
}