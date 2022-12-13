package com.example.sotisproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.sotisproject.dto.AnswerConceptDTO;
import com.example.sotisproject.model.Answer;
import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.repository.ConceptRepository;
import com.example.sotisproject.repository.StudentRepository;
import com.example.sotisproject.repository.TestRepository;

import lombok.AllArgsConstructor;

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
    
    public List<AnswerConceptDTO> getStudentAnswersForTest(Long studentId,Long testId){
        List<Answer> studentAnswers = studentRepository.findById(studentId).get().getAnswers();
        List<AnswerConceptDTO> studentTestAnswers = new ArrayList<>();
        studentAnswers.forEach(answer-> {
        	if(answer.getQuestion().getTest().getId() != testId) {
        		studentTestAnswers.add(new AnswerConceptDTO(answer,answer.getQuestion().getConcept()));
        	}
        });
        return studentTestAnswers;
    }
    
    public Set<Student> getStudentsForTest(Long testId){
        Set<Question> testQuestions = testRepository.findById(testId).get().getQuestions();
        Set<Student> students = new HashSet<>();
        testQuestions.forEach(question -> {
        	if(question.getTest().getId() == testId) {
        		question.getAnswers().forEach(answer ->{
        			students.addAll(answer.getStudents());
        		});
        	}
        });
        return students;
    }
}
