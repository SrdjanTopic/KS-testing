package com.example.sotisproject.repository;

import com.example.sotisproject.model.TestPublication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestPublicationRepository extends JpaRepository<TestPublication, Long> {
    List<TestPublication> findAllByTestId(Long testId);
}
