package com.example.sotisproject.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sotisproject.service.QuestionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionController {
	
	QuestionService questionService;
	
	@GetMapping("/qti/{id}")
    public ResponseEntity<Resource> qti(@PathVariable("id") Long questionId)
    {
       InputStreamResource resource=null;
       File qti =questionService.qti(questionId).getFile();
	try {
		resource = new InputStreamResource(new FileInputStream(qti));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       if(resource==null){
    	   return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       }
       return ResponseEntity.ok()
        .contentLength(qti.length())
		.contentType(MediaType.APPLICATION_OCTET_STREAM)
		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=questionQTI.xml")
		.body(resource);
    }

}
