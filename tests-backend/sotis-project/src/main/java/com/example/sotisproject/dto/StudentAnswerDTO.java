package com.example.sotisproject.dto;

import com.example.sotisproject.model.Answer;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.model.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentAnswerDTO {
    private Long answerId;
    private Question question;

    public StudentAnswerDTO(Answer answer){
        answerId = answer.getId();
        question = answer.getQuestion();
    };
}
