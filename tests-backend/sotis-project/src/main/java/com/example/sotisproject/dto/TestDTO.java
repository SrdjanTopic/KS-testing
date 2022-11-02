package com.example.sotisproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {
    private Long id;
    private String name;
    private Set<QuestionDTO> questions;
}
