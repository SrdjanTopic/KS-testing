package com.example.sotisproject.controller;

import com.example.sotisproject.model.Concept;
import com.example.sotisproject.service.ConceptService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/concepts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConceptController {
    private ConceptService conceptService;

    @GetMapping("/")
    public List<Concept> getTests(){
        return conceptService.getConcepts();
    }
}
