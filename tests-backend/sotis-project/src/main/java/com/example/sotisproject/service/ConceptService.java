package com.example.sotisproject.service;

//import com.example.sotisproject.jena.service.OntologyService;
import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.model.RealRelation;
import com.example.sotisproject.model.Relation;
import com.example.sotisproject.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ConceptService {
    private ConceptRepository conceptRepository;
    private QuestionRepository questionRepository;
    private TestRepository testRepository;
    private RelationRepository relationRepository;
    private RealRelationRepository realRelationRepository;
    private ProfessionRepository professionRepository;
//    private OntologyService ontologyService;

    public List<Concept> getConcepts() {
        return conceptRepository.findAll();
    }

    public List<Concept> addConcepts(List<Concept> concepts) {
        List<Concept> conceptList = new ArrayList<>();
        concepts.forEach((concept -> conceptList.add(conceptRepository.save(concept))));
//        ontologyService.addConcepts(conceptList);
        return conceptList;
    }

    public List<Concept> deleteConcepts(List<Concept> concepts) {
        List<Concept> conceptList = new ArrayList<>();
        concepts.forEach(concept -> {
            List<Question> questionList = questionRepository.findAllByConceptId(concept.getId());
            questionList.forEach((question -> {
                question.setConcept(null);
                questionRepository.save(question);
            }));
            conceptRepository.deleteById(concept.getId());
            conceptList.add(concept);
        });
//        ontologyService.deleteConcepts(conceptList);
        return conceptList;
    }

    public Set<Concept> getConceptsForTest(Long testId) {
        Set<Concept> concepts = new HashSet<>();
        testRepository.findById(testId).get().getQuestions().forEach(question -> {
                concepts.add(question.getConcept());
        });
        return concepts;
    }
    
    public Set<Concept> getConceptsByProfession(Long professionId) {
        Set<Concept> concepts=new HashSet<>();
        professionRepository.findById(professionId).get().getRequiredConcepts().forEach(concept -> {
            concepts.add(concept);
        });
        return concepts;
    }

    public List<Concept> getAllPreviousConceptsForConcept(String conceptName){
        List<Relation> relations = relationRepository.findAllByDestinationConcept(conceptName);
        List<Concept> returnConcepts = new ArrayList<>();
        relations.forEach(relation -> returnConcepts.add(relation.getSource()));
        return returnConcepts;
    }

    public List<Concept> getAllNextConceptsForConcept(String conceptName){
        List<Relation> relations = relationRepository.findAllBySourceConcept(conceptName);
        List<Concept> returnConcepts = new ArrayList<>();
        relations.forEach(relation -> returnConcepts.add(relation.getDestination()));
        return returnConcepts;
    }

    public List<Long> getConceptOrder(Long ksId){
        List<Long> sources = new ArrayList<>();
        List<Long> destinations = new ArrayList<>();
        List<Long> conceptOrder = new ArrayList<>();
        List<Long> currentConcepts = new ArrayList<>();
        List<Long> tempCurr = new ArrayList<>();
        if(ksId==0){
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
        }
        else {
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
        }
        return conceptOrder;
    }
}
