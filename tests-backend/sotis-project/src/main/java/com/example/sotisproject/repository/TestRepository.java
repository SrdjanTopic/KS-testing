package com.example.sotisproject.repository;

import com.example.sotisproject.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TestRepository extends JpaRepository<Test, Long> {
}
