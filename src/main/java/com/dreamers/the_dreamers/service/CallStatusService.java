package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.CallStatus;
import com.dreamers.the_dreamers.repository.CallStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CallStatusService {
    
    private final CallStatusRepository callStatusRepository;
    
    public List<CallStatus> getAllCallStatuses() {
        return callStatusRepository.findAll();
    }
    
    public Optional<CallStatus> getCallStatusById(String id) {
        return callStatusRepository.findById(id);
    }
    
    public Optional<CallStatus> getCallStatusByStatusName(String statusName) {
        return callStatusRepository.findByStatusName(statusName);
    }
    
    public CallStatus createCallStatus(CallStatus callStatus) {
        return callStatusRepository.save(callStatus);
    }
    
    public CallStatus updateCallStatus(String id, CallStatus callStatusDetails) {
        CallStatus callStatus = callStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CallStatus not found"));
        
        callStatus.setStatusName(callStatusDetails.getStatusName());
        
        return callStatusRepository.save(callStatus);
    }
    
    public void deleteCallStatus(String id) {
        callStatusRepository.deleteById(id);
    }
}
