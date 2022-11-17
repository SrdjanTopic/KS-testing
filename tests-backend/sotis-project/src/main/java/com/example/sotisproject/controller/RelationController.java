package com.example.sotisproject.controller;

import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Relation;
import com.example.sotisproject.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/relations", produces = MediaType.APPLICATION_JSON_VALUE)
public class RelationController {
    private RelationService relationService;

    @GetMapping("/")
    public List<Relation> getRelations(){
        return relationService.getRelations();
    }

    @PostMapping("/update")
    public List<Relation>addConcepts(@RequestBody List<Relation> relations){
        return relationService.updateRelations(relations);
    }
}
