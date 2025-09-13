package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

    UserDetails findByUserId(Long userId);
}
