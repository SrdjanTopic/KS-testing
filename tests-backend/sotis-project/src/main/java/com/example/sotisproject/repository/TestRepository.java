package com.example.sotisproject.repository;

import com.example.sotisproject.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TestRepository extends JpaRepository<Test, Long> {
    Test findByIdAndTestPublicationsIsPublishedTrue(Long testId);
    List<Test> findAllByTestPublicationsIsPublishedTrue();
    List<Test> findAllByTestPublicationsNull();
    List<Test> findAllByTeacherId(Long teacherId);
}
