package com.example.sotisproject.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Student extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Answer> answers;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Concept> learnedConcepts;
}
