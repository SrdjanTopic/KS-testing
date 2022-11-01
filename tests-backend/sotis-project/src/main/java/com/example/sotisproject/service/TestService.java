package com.example.sotisproject.service;

import com.example.sotisproject.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TestService {
    private TestRepository testRepository;
}
