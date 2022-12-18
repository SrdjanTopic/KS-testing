package com.example.sotisproject.controller;

import java.security.Principal;
import java.util.List;

import com.example.sotisproject.model.User;
import com.example.sotisproject.service.UserService;
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
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(@PathVariable("id") Long id){
         return new ResponseEntity<Student>(studentService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/answers")
    public ResponseEntity<List<StudentAnswerDTO>> findAnswersForStudent(@PathVariable("id") Long id){
        return new ResponseEntity<List<StudentAnswerDTO>>(studentService.findAnswersForStudent(id), HttpStatus.OK);
    }

    @GetMapping("/tests")
    public List<StudentTestDTO> findTestsForStudent(Principal user){
        if(user!=null){
            User loggedInUser = userService.findByUsername(user.getName());
            return studentService.findTestsForStudent(loggedInUser.getId());
        }
        return null;
    }

    @GetMapping("/{userId}/tests/{testId}")
    public List<StudentAnswerDTO> findTestForStudent(@PathVariable Long userId, @PathVariable Long testId){
        return studentService.getSubmittedTestForStudent(userId, testId);
    }
    
}
