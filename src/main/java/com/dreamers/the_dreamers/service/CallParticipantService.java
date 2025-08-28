package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.CallParticipant;
import com.dreamers.the_dreamers.repository.CallParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CallParticipantService {
    
    private final CallParticipantRepository callParticipantRepository;
    
    public List<CallParticipant> getAllCallParticipants() {
        return callParticipantRepository.findAll();
    }
    
    public Optional<CallParticipant> getCallParticipantById(String id) {
        return callParticipantRepository.findById(id);
    }


    
    public CallParticipant createCallParticipant(CallParticipant callParticipant) {
        return callParticipantRepository.save(callParticipant);
    }
    
    public CallParticipant updateCallParticipant(String id, CallParticipant callParticipantDetails) {
        CallParticipant callParticipant = callParticipantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CallParticipant not found"));
        
        callParticipant.setJoinedAt(callParticipantDetails.getJoinedAt());
        callParticipant.setLeftAt(callParticipantDetails.getLeftAt());
        
        return callParticipantRepository.save(callParticipant);
    }
    
    public void deleteCallParticipant(String id) {
        callParticipantRepository.deleteById(id);
    }
}
