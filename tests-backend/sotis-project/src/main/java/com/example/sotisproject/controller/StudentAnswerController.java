package com.example.sotisproject.controller;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sotisproject.dto.AnswerConceptDTO;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.service.StudentAnswerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/studentAnswers", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentAnswerController {
    private StudentAnswerService studentAnswerService;

    @GetMapping("/allResults")
    public Map<Long, List<Long>> getAllResults(){
        return studentAnswerService.getAllResultsForIITA();
    }

    @GetMapping("/allResults/{testId}")
    public Map<Long, List<Long>> getAllResultsForTest(@PathVariable("testId")Long testId){
        return studentAnswerService.getAllTestResultsForIITA(testId);
    }
    
    @GetMapping("/{studentId}/test/{testId}")
    public List<AnswerConceptDTO> getStudentAnswersForTest(@PathVariable("studentId") Long studentId,@PathVariable("testId") Long testId){
        return studentAnswerService.getStudentAnswersForTest(studentId,testId);
    }
    
    @GetMapping("/test/{id}")
    public ResponseEntity<Set<Student>> findStudentsForTest(@PathVariable("id") Long id){
        return new ResponseEntity<Set<Student>>(studentAnswerService.getStudentsForTest(id), HttpStatus.OK);
    }
}
