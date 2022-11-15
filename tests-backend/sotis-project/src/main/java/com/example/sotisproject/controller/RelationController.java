package com.example.sotisproject.controller;

import com.example.sotisproject.model.Relation;
import com.example.sotisproject.service.RelationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/relations", produces = MediaType.APPLICATION_JSON_VALUE)
public class RelationController {
    private RelationService relationService;

    @GetMapping("/")
    public List<Relation> getTests(){
        return relationService.getRelations();
    }
}
