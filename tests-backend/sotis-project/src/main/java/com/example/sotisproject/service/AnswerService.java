package com.example.sotisproject.service;

import com.example.sotisproject.dto.AnswerDTO;
import com.example.sotisproject.model.Answer;
import com.example.sotisproject.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AnswerService {
    private AnswerRepository answerRepository;

    public Answer addAnswer(AnswerDTO answerDTO){
        return answerRepository.insertAnswer(answerDTO.getQuestion_id(), answerDTO.getAnswer(), answerDTO.getIs_correct());
    }
}