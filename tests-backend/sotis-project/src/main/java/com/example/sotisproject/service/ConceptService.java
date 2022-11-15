package com.example.sotisproject.service;

import com.example.sotisproject.model.Concept;
import com.example.sotisproject.repository.ConceptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ConceptService {
    private ConceptRepository conceptRepository;

    public List<Concept> getConcepts() {
        return conceptRepository.findAll();
    }
}
