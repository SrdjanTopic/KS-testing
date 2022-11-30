package com.example.sotisproject.dto;

import com.example.sotisproject.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubmitAnswersDTO {
    private Long studentId;
    private Set<Answer> answers;
}
