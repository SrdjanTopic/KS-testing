package com.example.sotisproject.repository;

import com.example.sotisproject.model.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptRepository extends JpaRepository<Concept, Long> {
    public Concept findConceptByConcept(String concept);
}
