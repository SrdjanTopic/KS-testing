package com.example.sotisproject.service;

import com.example.sotisproject.model.Answer;
import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.repository.AnswerRepository;
import com.example.sotisproject.repository.ConceptRepository;
import com.example.sotisproject.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class StudentAnswerService {
    private StudentRepository studentRepository;
    private ConceptRepository conceptRepository;

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
}
