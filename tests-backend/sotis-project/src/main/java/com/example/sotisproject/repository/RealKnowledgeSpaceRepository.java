package com.example.sotisproject.repository;

import com.example.sotisproject.model.RealKnowledgeSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealKnowledgeSpaceRepository extends JpaRepository<RealKnowledgeSpace, Long> {
    List<RealKnowledgeSpace> findAllByTestId(Long testId);
}
