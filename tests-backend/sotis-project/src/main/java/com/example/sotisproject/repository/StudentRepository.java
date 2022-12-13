package com.example.sotisproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sotisproject.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	 List<Student> findByTests_Id(Long testId);
}
