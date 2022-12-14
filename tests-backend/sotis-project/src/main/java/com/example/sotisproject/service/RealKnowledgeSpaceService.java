package com.example.sotisproject.service;

import com.example.sotisproject.dto.RealKnowledgeSpaceDTO;
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
public class RealKnowledgeSpaceService {
    private RealKnowledgeSpaceRepository realKnowledgeSpaceRepository;
    private TestRepository testRepository;
    private RealRelationRepository relationRepository;
    private ConceptRepository conceptRepository;

    public List<RealKnowledgeSpaceDTO> findAllByTestId(Long testId){
        List<RealKnowledgeSpace> realKnowledgeSpaces = realKnowledgeSpaceRepository.findAllByTestId(testId);
        List<RealKnowledgeSpaceDTO> realKnowledgeSpaceDTOs = new ArrayList<>();
        realKnowledgeSpaces.forEach(realKnowledgeSpace ->{
            RealKnowledgeSpaceDTO rksdto = new RealKnowledgeSpaceDTO();
            rksdto.setTest(realKnowledgeSpace.getTest());
            rksdto.setId(realKnowledgeSpace.getId());
            rksdto.setRelations(realKnowledgeSpace.getRelations());
            rksdto.setCreationDate(realKnowledgeSpace.getCreationDate());
            realKnowledgeSpaceDTOs.add(rksdto);
        });
        return realKnowledgeSpaceDTOs;
    }

    public RealKnowledgeSpace createKnowledgeSpaceForTest(List<RealRelationsDTO> realRelationsDTOS, Long testID){
        Test test = testRepository.findById(testID).get();
        RealKnowledgeSpace rks = realKnowledgeSpaceRepository.save(new RealKnowledgeSpace(null, LocalDateTime.now(), test, null, null));
        List<RealRelation> relationList = new ArrayList<>();
        realRelationsDTOS.forEach((relation -> {
            RealRelation realRelation = new RealRelation(null, null, null, null);
            realRelation.setRealSource(conceptRepository.findById(relation.getSourceId()).get());
            realRelation.setRealDestination(conceptRepository.findById(relation.getDestinationId()).get());
            realRelation.setRealKnowledgeSpace(rks);
            relationList.add(relationRepository.save(realRelation));
        }));
        rks.setRelations(relationList);
        return rks;
    }
}
