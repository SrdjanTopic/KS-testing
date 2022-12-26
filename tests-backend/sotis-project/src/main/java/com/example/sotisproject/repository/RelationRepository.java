package com.example.sotisproject.repository;

import com.example.sotisproject.model.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelationRepository extends JpaRepository<Relation, Long> {
    List<Relation> findAllBySourceConcept(String concept);
    List<Relation> findAllByDestinationConcept(String concept);
}
