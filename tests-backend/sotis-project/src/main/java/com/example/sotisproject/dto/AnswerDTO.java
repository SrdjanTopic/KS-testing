package com.example.sotisproject.dto;

import com.example.sotisproject.model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private Long id;
    private Question question;
    private String answer;
    private Boolean is_correct;
}
