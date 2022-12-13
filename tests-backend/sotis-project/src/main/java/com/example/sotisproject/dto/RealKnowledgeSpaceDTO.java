package com.example.sotisproject.dto;

import com.example.sotisproject.model.RealRelation;
import com.example.sotisproject.model.Test;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RealKnowledgeSpaceDTO {
    private Long id;

    private LocalDateTime creationDate;

    private Test test;

    private List<RealRelation> relations;
}
