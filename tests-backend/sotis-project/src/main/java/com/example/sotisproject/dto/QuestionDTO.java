package com.example.sotisproject.dto;

import com.example.sotisproject.model.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long id;
    private Test test;
    private String question;
    private Integer points;
    private Set<AnswerDTO> answers;
}
