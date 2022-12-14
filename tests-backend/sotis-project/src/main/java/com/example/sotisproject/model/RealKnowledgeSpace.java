package com.example.sotisproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RealKnowledgeSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Test test;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "realKnowledgeSpace", cascade = CascadeType.ALL)
    private List<RealRelation> relations;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "realKnowledgeSpace", cascade = CascadeType.ALL)
    private List<TestPublication> testPublications;
}
