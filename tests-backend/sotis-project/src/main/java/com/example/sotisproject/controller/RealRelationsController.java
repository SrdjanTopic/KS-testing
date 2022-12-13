package com.example.sotisproject.controller;

import com.example.sotisproject.dto.RealRelationsDTO;
import com.example.sotisproject.model.RealRelation;
import com.example.sotisproject.service.RealRelationService;
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

    @GetMapping("/{KSId}")
    public List<RealRelation> getRelationsForTest(@PathVariable("KSId") Long KSId){
        return relationService.findAllByRealKnowledgeSpaceId(KSId);
    }

    @PostMapping("/{KSId}/create")
    public List<RealRelation>addConcepts(
            @PathVariable("KSId") Long KSId,
            @RequestBody List<RealRelationsDTO> realRelationsDTOS){
        return relationService.createRealRelations(realRelationsDTOS, KSId);
    }
}
