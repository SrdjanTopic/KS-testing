package com.example.sotisproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String question;

    @Column(columnDefinition = "integer default 10")
    private Integer points;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Test test;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Answer> answers;

    @ManyToOne(fetch = FetchType.EAGER)
    private Concept concept;

}
