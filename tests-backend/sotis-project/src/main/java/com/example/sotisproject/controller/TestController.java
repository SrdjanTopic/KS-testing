package com.example.sotisproject.controller;

import com.example.sotisproject.dto.SubmitAnswersDTO;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import java.util.List;
import org.springframework.web.bind.annotation.*;

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

import com.example.sotisproject.model.Test;
import com.example.sotisproject.service.TestService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/tests", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    private TestService testService;

    @GetMapping("/")
    public List<Test> getTests(){
        return testService.getTests();
    }

    @PostMapping("/add")
    public Test addTest(@RequestBody Test test){
        return testService.addTest(test);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTest(@PathVariable("id") Long id){
        return new ResponseEntity<Test>(testService.getTest(id), HttpStatus.OK);
    }
    
    @PostMapping("/submit")
    public ResponseEntity<Student> submitTest(@RequestBody SubmitAnswersDTO submitAnswersDTO){
        return new ResponseEntity<Student>(testService.submitTest(submitAnswersDTO), HttpStatus.OK);
    }
}
