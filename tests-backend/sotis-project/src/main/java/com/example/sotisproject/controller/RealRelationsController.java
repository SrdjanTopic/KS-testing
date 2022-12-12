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

    @GetMapping("/{testId}")
    public List<RealRelation> getRelationsForTest(@PathVariable("testId") Long testId){
        return relationService.getRealRelationsForTest(testId);
    }

    @PostMapping("/{testId}/create")
    public List<RealRelation>addConcepts(
            @PathVariable("testId") Long testId,
            @RequestBody List<RealRelationsDTO> realRelationsDTOS){
        return relationService.createRealRelations(realRelationsDTOS, testId);
    }
}
