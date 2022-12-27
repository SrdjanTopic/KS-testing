package com.example.sotisproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sotisproject.model.Profession;
import com.example.sotisproject.repository.ProfessionRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProfessionService {
	private ProfessionRepository professionRepository;
	
	 public List<Profession> getProfessions() {
	        return professionRepository.findAll();
	    }
}
