package com.example.sotisproject.repository;

import com.example.sotisproject.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {
}
