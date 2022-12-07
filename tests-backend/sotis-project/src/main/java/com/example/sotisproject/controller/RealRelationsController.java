package com.example.sotisproject.controller;

import com.example.sotisproject.dto.RealRelationsDTO;
import com.example.sotisproject.model.RealRelation;
import com.example.sotisproject.model.Relation;
import com.example.sotisproject.service.RealRelationService;
import com.example.sotisproject.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/realRelations", produces = MediaType.APPLICATION_JSON_VALUE)
public class RealRelationsController {
    private RealRelationService relationService;

    @GetMapping("/")
    public List<RealRelation> getRelations(){
        return relationService.getRealRelations();
    }

    @PostMapping("/create")
    public List<RealRelation>addConcepts(@RequestBody List<RealRelationsDTO> realRelationsDTOS){
        return relationService.createRealRelations(realRelationsDTOS);
    }
}
