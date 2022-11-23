package com.example.sotisproject.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
	private Set<Test> tests;

}
