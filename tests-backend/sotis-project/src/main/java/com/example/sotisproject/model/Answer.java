package com.example.sotisproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;

    private String answer;

    @Column(columnDefinition = "boolean default false")
    private Boolean isCorrect;

    @JsonIgnore
    @ManyToMany(mappedBy = "answers", fetch = FetchType.EAGER)
    private Set<Student> students;

}
