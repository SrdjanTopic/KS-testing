package com.example.sotisproject.repository;

import com.example.sotisproject.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Transactional
    @Modifying
    @Query(value = "insert into Answer (question_id, answer, is_correct) values (:question_id, :answer, :is_correct)",
            nativeQuery = true)
    void insertAnswer(@Param("question_id") Long question_id, @Param("answer") String answer,
                      @Param("is_correct") Boolean is_correct);
}
