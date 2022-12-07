package com.example.sotisproject.controller;


import com.example.sotisproject.model.Student;
import com.example.sotisproject.service.StudentAnswerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/studentAnswers", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentAnswerController {
    private StudentAnswerService studentAnswerService;

    @GetMapping("/allResults")
    public Map<Long, List<Long>> getAllResults(){
        return studentAnswerService.getAllResultsForIITA();
    }
}
