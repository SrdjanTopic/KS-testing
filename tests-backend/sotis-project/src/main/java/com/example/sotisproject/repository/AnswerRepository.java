package com.example.sotisproject.repository;

import com.example.sotisproject.dto.AnswerDTO;
import com.example.sotisproject.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Modifying
    @Query(value = "insert into Users (question_id, answer, is_correct) "
            + "values (:question_id, :answer, :is_correct)",
            nativeQuery = true)
    Answer insertAnswer(@Param("question_id") Integer question_id, @Param("answer") String answer,
                      @Param("is_correct") Boolean is_correct);
}
