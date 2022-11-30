package com.example.sotisproject.service;

import com.example.sotisproject.dto.SubmitAnswersDTO;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.repository.StudentRepository;
import com.example.sotisproject.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class TestService {
    private TestRepository testRepository;
    private QuestionService questionService;
    private StudentRepository studentRepository;

    public Test addTest(Test test){
        Test newTest = testRepository.save(test);
        test.getQuestions().forEach((Question question)->{
            question.setTest(newTest);
            questionService.addQuestion(question);
        });
        return newTest;
    }
    
    public Student submitTest(SubmitAnswersDTO submitAnswersDTO){
        Student student = studentRepository.findById(submitAnswersDTO.getStudentId()).get();
        student.setAnswers(submitAnswersDTO.getAnswers());
        return studentRepository.save(student);
    }

    public List<Test> getTests() {
        return testRepository.findAll();
    }
    
    public Test getTest(Long id) {
        return testRepository.findById(id).get();
    }
}
