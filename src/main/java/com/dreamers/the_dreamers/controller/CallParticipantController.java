package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.CallParticipant;
import com.dreamers.the_dreamers.service.CallParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/call-participants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CallParticipantController {
    
    private final CallParticipantService callParticipantService;
    
    @GetMapping
    public ResponseEntity<List<CallParticipant>> getAllCallParticipants() {
        return ResponseEntity.ok(callParticipantService.getAllCallParticipants());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CallParticipant> getCallParticipantById(@PathVariable String id) {
        return callParticipantService.getCallParticipantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<CallParticipant> createCallParticipant(@RequestBody CallParticipant callParticipant) {
        return ResponseEntity.ok(callParticipantService.createCallParticipant(callParticipant));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CallParticipant> updateCallParticipant(@PathVariable String id, @RequestBody CallParticipant callParticipantDetails) {
        try {
            CallParticipant updatedCallParticipant = callParticipantService.updateCallParticipant(id, callParticipantDetails);
            return ResponseEntity.ok(updatedCallParticipant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCallParticipant(@PathVariable String id) {
        callParticipantService.deleteCallParticipant(id);
        return ResponseEntity.noContent().build();
    }
}
