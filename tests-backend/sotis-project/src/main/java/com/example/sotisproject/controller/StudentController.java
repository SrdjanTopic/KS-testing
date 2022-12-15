package com.example.sotisproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sotisproject.dto.StudentAnswerDTO;
import com.example.sotisproject.dto.StudentTestDTO;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(@PathVariable("id") Long id){
         return new ResponseEntity<Student>(studentService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/answers")
    public ResponseEntity<List<StudentAnswerDTO>> findAnswersForStudent(@PathVariable("id") Long id){
        return new ResponseEntity<List<StudentAnswerDTO>>(studentService.findAnswersForStudent(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/tests")
    public ResponseEntity<List<StudentTestDTO>> findTestsForStudent(@PathVariable("id") Long id){
        return new ResponseEntity<List<StudentTestDTO>>(studentService.findTestsForStudent(id), HttpStatus.OK);
    }
    
}
