package com.example.sotisproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sotisproject.dto.SubmitAnswersDTO;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.service.QTIService;
import com.example.sotisproject.service.TestService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/tests", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    private TestService testService;
    private QTIService qtiService;
    
    @GetMapping("/")
    public List<Test> getTests(){
        return testService.getTests();
    }

    @GetMapping("/published")
    public List<Test> getAllPublishedTests(){
        return testService.getAllPublishedTests();
    }

    @GetMapping("/unpublished")
    public List<Test> getAllUnpublishedTests(){
        return testService.getAllUnpublishedTests();
    }
    
    @GetMapping("/{id}/teacher")
    public List<String> getAllTestsByTeacher(@PathVariable("id") Long id){
        return testService.getTestsByTeacher(id);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTest(@PathVariable("id") Long id){
        return new ResponseEntity<Test>(testService.getTest(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/published")
    public ResponseEntity<Test> getPublishedTestById(@PathVariable("id") Long id){
        return new ResponseEntity<Test>(testService.getPublishedTestById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public Test addTest(@RequestBody Test test){
        return testService.addTest(test);
    }
    
    @PostMapping("/submit")
    public ResponseEntity<Student> submitTest(@RequestBody SubmitAnswersDTO submitAnswersDTO){
        return new ResponseEntity<Student>(testService.submitTest(submitAnswersDTO), HttpStatus.OK);
    }
    
    @GetMapping("/generateQTI/{id}")
	public ResponseEntity<byte[]> generateQTI(@PathVariable Long id) {
		return new ResponseEntity<>(qtiService.generateQTI(id), HttpStatus.OK);
	}
}
