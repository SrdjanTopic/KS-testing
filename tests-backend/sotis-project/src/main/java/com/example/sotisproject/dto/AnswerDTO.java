package com.example.sotisproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private Integer question_id;
    private String answer;
    private Boolean is_correct;
}
