package com.example.sotisproject.repository;

import com.example.sotisproject.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Transactional
    @Modifying
    @Query(value = "insert into Question (test_id, question, points) values (:test_id, :question, :points)",
            nativeQuery = true)
    void insertQuestion(@Param("test_id") Long test_id, @Param("question") String question,
                      @Param("points") Integer points);
}
