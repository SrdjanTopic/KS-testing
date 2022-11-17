package com.example.sotisproject.controller;

import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.service.ConceptService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/concepts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConceptController {
    private ConceptService conceptService;

    @GetMapping("/")
    public List<Concept> getConcepts(){
        return conceptService.getConcepts();
    }

    @PostMapping("/add")
    public List<Concept> addConcepts(@RequestBody List<Concept> concepts){
        return conceptService.addConcepts(concepts);
    }
    @PostMapping("/delete")
    public void deleteConcepts(@RequestBody List<Concept> concepts){
        concepts.forEach((concept -> System.out.println(concept.getId())));
        conceptService.deleteConcepts(concepts);
    }
}
