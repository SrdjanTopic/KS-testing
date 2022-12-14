package com.example.sotisproject.service;

import com.example.sotisproject.dto.RealRelationsDTO;
import com.example.sotisproject.model.RealKnowledgeSpace;
import com.example.sotisproject.model.RealRelation;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.repository.ConceptRepository;
import com.example.sotisproject.repository.RealKnowledgeSpaceRepository;
import com.example.sotisproject.repository.RealRelationRepository;
import com.example.sotisproject.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RealRelationService {
    private RealRelationRepository realRelationRepository;
    private ConceptRepository conceptRepository;
    private TestRepository testRepository;
    private RealKnowledgeSpaceRepository realKnowledgeSpaceRepository;

    public List<RealRelation> getRealRelations() {
        return realRelationRepository.findAll();
    }

    public List<RealRelation> findAllByRealKnowledgeSpaceId(Long testId) {
        return realRelationRepository.findAllByRealKnowledgeSpaceId(testId);
    }

    public List<RealRelation> createRealRelations(List<RealRelationsDTO> realRelationsDTOS, Long testID) {
        Test test = testRepository.findById(testID).get();
        RealKnowledgeSpace realKnowledgeSpace = new RealKnowledgeSpace(null, LocalDateTime.now(), test, null, null);
        RealKnowledgeSpace rks = realKnowledgeSpaceRepository.save(realKnowledgeSpace);
        List<RealRelation> relationList = new ArrayList<>();
        realRelationsDTOS.forEach((relation -> {
            RealRelation realRelation = new RealRelation(null, null, null, null);
            realRelation.setRealSource(conceptRepository.findById(relation.getSourceId()).get());
            realRelation.setRealDestination(conceptRepository.findById(relation.getDestinationId()).get());
            realRelation.setRealKnowledgeSpace(rks);
            relationList.add(realRelationRepository.save(realRelation));
        }));
        return relationList;
    }

    public List<Long> getConceptOrderForKnowledgeSpace(Long ksId){
        List<Long> sources = new ArrayList<>();
        List<Long> destinations = new ArrayList<>();
        List<Long> conceptOrder = new ArrayList<>();
        List<Long> currentConcepts = new ArrayList<>();
        List<Long> tempCurr = new ArrayList<>();
        List<RealRelation> relationList = realRelationRepository.findAllByRealKnowledgeSpaceId(ksId);
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

