package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.VideoCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoCallRepository extends JpaRepository<VideoCall, String> {
    List<VideoCall> findByOrganizerId(Long organizerId);
}
