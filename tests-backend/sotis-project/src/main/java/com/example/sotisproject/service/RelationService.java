package com.example.sotisproject.service;

import com.example.sotisproject.model.Relation;
import com.example.sotisproject.repository.RelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RelationService {
    private RelationRepository relationRepository;
    public List<Relation> getRelations() {
        return relationRepository.findAll();
    }
}
