package com.example.sotisproject.service;

import com.example.sotisproject.model.Concept;
import com.example.sotisproject.repository.ConceptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ConceptService {
    private ConceptRepository conceptRepository;

    public List<Concept> getConcepts() {
        return conceptRepository.findAll();
    }

    public List<Concept> addConcepts(List<Concept> concepts) {
        List<Concept> conceptList = new ArrayList<>();
        concepts.forEach((concept -> conceptList.add(conceptRepository.save(concept))));
        return conceptList;
    }

    public void deleteConcepts(List<Concept> concepts) {
        concepts.forEach((concept -> conceptRepository.deleteById(concept.getId())));
    }
}
