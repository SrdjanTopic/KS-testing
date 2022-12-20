package com.example.sotisproject.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sotisproject.model.Question;
import com.example.sotisproject.service.QuestionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionController {
	
	QuestionService questionService;
	
	@PostMapping("/qti")
    public ResponseEntity<FileSystemResource> qti(@RequestBody Question question)
    {
       FileSystemResource resource = questionService.qti(question);
       if(resource==null){
    	   return new ResponseEntity<FileSystemResource>(resource, HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<FileSystemResource>(resource, HttpStatus.OK);
    }

}
