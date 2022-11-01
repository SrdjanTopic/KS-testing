package com.example.sotisproject.service;

import com.example.sotisproject.model.Question;
import com.example.sotisproject.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class QuestionService {
    private QuestionRepository questionRepository;

}