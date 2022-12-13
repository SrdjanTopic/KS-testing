package com.example.sotisproject.repository;

import com.example.sotisproject.model.RealRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealRelationRepository extends JpaRepository<RealRelation, Long> {
    List<RealRelation> findAllByRealKnowledgeSpaceId(Long KSId);
}
