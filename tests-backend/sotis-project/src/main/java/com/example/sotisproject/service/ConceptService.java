package com.example.sotisproject.service;

import com.example.sotisproject.jena.service.OntologyService;
import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.repository.ConceptRepository;
import com.example.sotisproject.repository.QuestionRepository;
import com.example.sotisproject.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ConceptService {
    private ConceptRepository conceptRepository;
    private QuestionRepository questionRepository;
    private TestRepository testRepository;
    private OntologyService ontologyService;

    public List<Concept> getConcepts() {
        return conceptRepository.findAll();
    }

    public List<Concept> addConcepts(List<Concept> concepts) {
        List<Concept> conceptList = new ArrayList<>();
        concepts.forEach((concept -> conceptList.add(conceptRepository.save(concept))));
        ontologyService.addConcepts(conceptList);
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
        ontologyService.deleteConcepts(conceptList);
        return conceptList;
    }

    public List<Concept> getConceptsForTest(Long testId) {
        List<Concept> concepts = new ArrayList<>();
        testRepository.findById(testId).get().getQuestions().forEach(question -> {
            concepts.add(question.getConcept());
        });
        return concepts;
    }
}
