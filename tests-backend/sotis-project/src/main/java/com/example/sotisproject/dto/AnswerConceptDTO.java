package com.example.sotisproject.dto;

import com.example.sotisproject.model.Answer;
import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerConceptDTO {
	   private Answer answer;
	   private Question question;
}
