package com.example.sotisproject.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sotisproject.model.Profession;
import com.example.sotisproject.service.ProfessionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/professions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfessionController {
	private ProfessionService professionService;
	
	 @GetMapping("/")
	    public List<Profession> getConcepts(){
	        return professionService.getProfessions();
	    }
}
