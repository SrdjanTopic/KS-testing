package com.example.sotisproject.controller;

import com.example.sotisproject.dto.RealKnowledgeSpaceDTO;
import com.example.sotisproject.dto.RealRelationsDTO;
import com.example.sotisproject.model.RealKnowledgeSpace;
import com.example.sotisproject.model.RealRelation;
import com.example.sotisproject.service.RealKnowledgeSpaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/realKS", produces = MediaType.APPLICATION_JSON_VALUE)
public class RealKnowledgeSpaceController {
    private RealKnowledgeSpaceService realKnowledgeSpaceService;

    @GetMapping("/{testId}")
    List<RealKnowledgeSpaceDTO> findAllByTestId(@PathVariable("testId")Long testId){
        return realKnowledgeSpaceService.findAllByTestId(testId);
    }

    @PostMapping("/{testId}/create")
    public RealKnowledgeSpace createKnowledgeSpaceForTest(
            @PathVariable("testId") Long testId,
            @RequestBody List<RealRelationsDTO> realRelationsDTOS){
        return realKnowledgeSpaceService.createKnowledgeSpaceForTest(realRelationsDTOS, testId);
    }
}
