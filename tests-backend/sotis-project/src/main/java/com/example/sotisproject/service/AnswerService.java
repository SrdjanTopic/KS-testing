package com.example.sotisproject.service;

import com.example.sotisproject.model.Answer;
import com.example.sotisproject.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AnswerService {
    private AnswerRepository answerRepository;

    public Answer addAnswer(Answer answer){
        return answerRepository.save(new Answer(null, answer.getQuestion(), answer.getAnswer(), answer.getIsCorrect()));
        }
}