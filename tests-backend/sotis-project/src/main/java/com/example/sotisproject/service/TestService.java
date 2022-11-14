package com.example.sotisproject.service;

import com.example.sotisproject.model.Question;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class TestService {
    private TestRepository testRepository;
    private QuestionService questionService;

    public Test addTest(Test test){
        Test newTest = testRepository.save(new Test(null, test.getName(), null));
        test.getQuestions().forEach((Question question)->{
            question.setTest(newTest);
            questionService.addQuestion(question);
        });
        return newTest;
    }

    public List<Test> getTests() {
        return testRepository.findAll();
    }
}
