package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, String> {
    Optional<ReportType> findByTypeName(String typeName);
}
