package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.CallStatus;
import com.dreamers.the_dreamers.service.CallStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/call-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CallStatusController {
    
    private final CallStatusService callStatusService;
    
    @GetMapping
    public ResponseEntity<List<CallStatus>> getAllCallStatuses() {
        return ResponseEntity.ok(callStatusService.getAllCallStatuses());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CallStatus> getCallStatusById(@PathVariable String id) {
        return callStatusService.getCallStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{statusName}")
    public ResponseEntity<CallStatus> getCallStatusByStatusName(@PathVariable String statusName) {
        return callStatusService.getCallStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<CallStatus> createCallStatus(@RequestBody CallStatus callStatus) {
        return ResponseEntity.ok(callStatusService.createCallStatus(callStatus));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CallStatus> updateCallStatus(@PathVariable String id, @RequestBody CallStatus callStatusDetails) {
        try {
            CallStatus updatedCallStatus = callStatusService.updateCallStatus(id, callStatusDetails);
            return ResponseEntity.ok(updatedCallStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCallStatus(@PathVariable String id) {
        callStatusService.deleteCallStatus(id);
        return ResponseEntity.noContent().build();
    }
}
