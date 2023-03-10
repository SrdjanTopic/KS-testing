package com.example.sotisproject.controller;

import com.example.sotisproject.model.Concept;
import com.example.sotisproject.service.ConceptService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/concepts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConceptController {
    private ConceptService conceptService;

    @GetMapping("/")
    public List<Concept> getConcepts(){
        return conceptService.getConcepts();
    }

    @GetMapping("/{testId}")
    public Set<Concept> getConceptsForTest(@PathVariable("testId") Long testId){
        return conceptService.getConceptsForTest(testId);
    }
    
    @GetMapping("/profession/{professionId}")
    public Set<Concept> getConceptsByProfession(@PathVariable("professionId") Long professionId){
        return conceptService.getConceptsByProfession(professionId);
    }

    @PostMapping("/add")
    public List<Concept> addConcepts(@RequestBody List<Concept> concepts){
        return conceptService.addConcepts(concepts);
    }
    @PostMapping("/delete")
    public List<Concept> deleteConcepts(@RequestBody List<Concept> concepts){
        return conceptService.deleteConcepts(concepts);
    }
}
