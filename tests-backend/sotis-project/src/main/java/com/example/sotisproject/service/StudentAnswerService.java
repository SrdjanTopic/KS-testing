package com.example.sotisproject.service;

import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.repository.ConceptRepository;
import com.example.sotisproject.repository.StudentRepository;
import com.example.sotisproject.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class StudentAnswerService {
    private StudentRepository studentRepository;
    private ConceptRepository conceptRepository;
    private TestRepository testRepository;

    public Map<Long, List<Long>> getAllResultsForIITA(){
        Map<Long, List<Long>> results = new HashMap<>();
        List<Student> students = studentRepository.findAll();
        List<Concept> concepts = conceptRepository.findAll();
        concepts.forEach(concept -> results.put(concept.getId(),new ArrayList<>()));
        students.forEach(student -> {
            student.getAnswers().forEach(answer -> {
                if(answer.getIsCorrect()){
                    results.get(answer.getQuestion().getConcept().getId()).add(1L);
                }
                else {
                    results.get(answer.getQuestion().getConcept().getId()).add(0L);
                }
            });
        });
        return results;
    }

    public Map<Long, List<Long>> getAllTestResultsForIITA(Long testId) {
        Map<Long, List<Long>> results = new HashMap<>();
        List<Student> students = studentRepository.findAll();
        List<Long> answerIds = new ArrayList<>();
        Set<Long> conceptIds = new HashSet<>();
        testRepository.findById(testId).get().getQuestions().forEach(question -> {
            conceptIds.add(question.getConcept().getId());
            question.getAnswers().forEach(answer -> answerIds.add(answer.getId()));
        });
        conceptIds.forEach(aLong -> results.put(aLong, new ArrayList<>()));
        students.forEach(student -> {
            student.getAnswers().forEach(answer -> {
                if(answerIds.contains(answer.getId())) {
                    if (answer.getIsCorrect()) {
                        results.get(answer.getQuestion().getConcept().getId()).add(1L);
                    } else {
                        results.get(answer.getQuestion().getConcept().getId()).add(0L);
                    }
                }
            });
        });
        return results;
    }
}
