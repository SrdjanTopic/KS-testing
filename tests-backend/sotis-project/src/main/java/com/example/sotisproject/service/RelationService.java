package com.example.sotisproject.service;

import com.example.sotisproject.model.Relation;
import com.example.sotisproject.repository.RelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RelationService {
    private RelationRepository relationRepository;
    public List<Relation> getRelations() {
        return relationRepository.findAll();
    }

    public List<Relation> updateRelations(List<Relation> relations) {
        List<Relation> relationList = new ArrayList<>();
        relationRepository.deleteAll();
        relations.forEach((relation -> relationList.add(relationRepository.save(relation))));
        return relationList;
    }

    public List<Long> getConceptOrder(){
        List<Long> conceptOrder = new ArrayList<>();
        List<Relation> relationList = relationRepository.findAll();

        return conceptOrder;
    }
}
