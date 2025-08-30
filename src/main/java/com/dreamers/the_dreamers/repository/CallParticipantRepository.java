package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.CallParticipant;
import com.dreamers.the_dreamers.model.User;
import com.dreamers.the_dreamers.model.VideoCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallParticipantRepository extends JpaRepository<CallParticipant, String> {
    // Tìm tất cả CallParticipant dựa trên một VideoCall
    List<CallParticipant> findByVideoCall(VideoCall videoCall);

    // Tìm tất cả CallParticipant dựa trên một User
    List<CallParticipant> findByUser(User user);

    // Tìm CallParticipant dựa trên ID của VideoCall (cách cũ vẫn hoạt động, nhưng không chuẩn)
    List<CallParticipant> findByVideoCall_Id(String videoCallId);

    // Tìm CallParticipant dựa trên ID của User (cách cũ vẫn hoạt động, nhưng không chuẩn)
    List<CallParticipant> findByUser_Id(String userId);
}
