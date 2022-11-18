package com.example.sotisproject.repository;

import com.example.sotisproject.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByConceptId(Long conceptId);
}
