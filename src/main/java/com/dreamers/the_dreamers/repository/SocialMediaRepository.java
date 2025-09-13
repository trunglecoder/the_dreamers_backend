package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.SocialMedia;
import com.dreamers.the_dreamers.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMedia, String> {
    List<SocialMedia> findByUserDetails(UserDetails userDetails);
}
