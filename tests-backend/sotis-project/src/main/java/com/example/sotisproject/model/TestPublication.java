package com.example.sotisproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestPublication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isPublished;

    private LocalDateTime publicationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private RealKnowledgeSpace realKnowledgeSpace;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Test test;
}
