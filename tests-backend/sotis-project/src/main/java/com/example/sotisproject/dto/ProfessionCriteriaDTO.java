package com.example.sotisproject.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfessionCriteriaDTO {
	
	private List<String> conceptNames;
	private String profession;
}
