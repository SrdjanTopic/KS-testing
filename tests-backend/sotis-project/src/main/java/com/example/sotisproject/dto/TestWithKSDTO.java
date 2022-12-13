package com.example.sotisproject.dto;

import com.example.sotisproject.model.RealKnowledgeSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestWithKSDTO {
    private Long id;
    private String name;
    private List<RealKnowledgeSpace> realKnowledgeSpaces;

}
