package com.example.sotisproject.repository;

import com.example.sotisproject.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByStudentsId(Long conceptId);
}
