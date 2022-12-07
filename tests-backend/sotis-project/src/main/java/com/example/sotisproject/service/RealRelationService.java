package com.example.sotisproject.service;

import com.example.sotisproject.dto.RealRelationsDTO;
import com.example.sotisproject.model.RealRelation;
import com.example.sotisproject.repository.ConceptRepository;
import com.example.sotisproject.repository.RealRelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RealRelationService {
    private RealRelationRepository relationRepository;
    private ConceptRepository conceptRepository;

    public List<RealRelation> getRealRelations() {
        return relationRepository.findAll();
    }

    public List<RealRelation> createRealRelations(List<RealRelationsDTO> realRelationsDTOS) {
        List<RealRelation> relationList = new ArrayList<>();
        relationRepository.deleteAll();
        realRelationsDTOS.forEach((relation -> {
            RealRelation realRelation = new RealRelation(null, null, null);
            realRelation.setRealSource(conceptRepository.findById(relation.getSourceId()).get());
            realRelation.setRealDestination(conceptRepository.findById(relation.getDestinationId()).get());
            relationList.add(relationRepository.save(realRelation));
        }));
        return relationList;
    }

    public List<Long> getConceptOrder(){
        List<Long> sources = new ArrayList<>();
        List<Long> destinations = new ArrayList<>();
        List<Long> conceptOrder = new ArrayList<>();
        List<Long> currentConcepts = new ArrayList<>();
        List<Long> tempCurr = new ArrayList<>();
        List<RealRelation> relationList = relationRepository.findAll();
        relationList.forEach(relation -> {
            sources.add(relation.getRealSource().getId());
            destinations.add(relation.getRealDestination().getId());
        });
        sources.forEach(source->{if(!destinations.contains(source)&&!conceptOrder.contains(source)&&!currentConcepts.contains(source)) {
            conceptOrder.add(source);
            currentConcepts.add(source);
        }});
        sources.removeAll(currentConcepts);
        do{
            relationList.forEach(relation -> {
                if (currentConcepts.contains(relation.getRealSource().getId())&&!conceptOrder.contains(relation.getRealDestination().getId())) {
                    conceptOrder.add(relation.getRealDestination().getId());
                    tempCurr.add(relation.getRealDestination().getId());
                }
            });
            currentConcepts.clear();
            currentConcepts.addAll(tempCurr);
            sources.removeAll(currentConcepts);
        }while(sources.size()!=0);
        relationList.forEach(relation -> {
            if (currentConcepts.contains(relation.getRealSource().getId())&&!conceptOrder.contains(relation.getRealDestination().getId())) {
                conceptOrder.add(relation.getRealDestination().getId());
            }
        });
        return conceptOrder;
    }
}

