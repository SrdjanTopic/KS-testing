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
        List<Long> sources = new ArrayList<>();
        List<Long> destinations = new ArrayList<>();
        List<Long> conceptOrder = new ArrayList<>();
        List<Long> currentConcepts = new ArrayList<>();
        List<Long> tempCurr = new ArrayList<>();
        List<Relation> relationList = relationRepository.findAll();
        relationList.forEach(relation -> {
            sources.add(relation.getSource().getId());
            destinations.add(relation.getDestination().getId());
        });
        sources.forEach(source->{if(!destinations.contains(source)&&!conceptOrder.contains(source)&&!currentConcepts.contains(source)) {
            conceptOrder.add(source);
            currentConcepts.add(source);
        }});
        sources.removeAll(currentConcepts);
        do{
            relationList.forEach(relation -> {
                if (currentConcepts.contains(relation.getSource().getId())&&!conceptOrder.contains(relation.getDestination().getId())) {
                    conceptOrder.add(relation.getDestination().getId());
                    tempCurr.add(relation.getDestination().getId());
                }
            });
            currentConcepts.clear();
            currentConcepts.addAll(tempCurr);
            sources.removeAll(currentConcepts);
        }while(sources.size()!=0);
        relationList.forEach(relation -> {
            if (currentConcepts.contains(relation.getSource().getId())&&!conceptOrder.contains(relation.getDestination().getId())) {
                conceptOrder.add(relation.getDestination().getId());
            }
        });
        return conceptOrder;
    }
}
