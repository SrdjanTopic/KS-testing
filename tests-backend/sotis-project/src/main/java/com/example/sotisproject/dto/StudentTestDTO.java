package com.example.sotisproject.dto;

import com.example.sotisproject.model.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentTestDTO {
    private Long id;
    private String name;
    private List<StudentAnswerDTO> questions;

    public StudentTestDTO(Test test){
        id = test.getId();
        name = test.getName();
        questions = new ArrayList<>();
        test.getQuestions().forEach(question -> question.getAnswers().forEach(answer -> questions.add(new StudentAnswerDTO(answer))));
    }
}
