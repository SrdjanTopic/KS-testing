package com.example.sotisproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "test", fetch = FetchType.EAGER)
    private Set<Question> questions;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Teacher teacher;

    @OneToMany(mappedBy = "test", fetch = FetchType.EAGER)
    private List<RealKnowledgeSpace> realKnowledgeSpaces;

    @OneToMany(mappedBy = "test", fetch = FetchType.EAGER)
    private List<TestPublication> testPublications;
}
