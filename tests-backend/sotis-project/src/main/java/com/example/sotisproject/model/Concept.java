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
public class Concept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String concept;

    @JsonIgnore
    @OneToMany(mappedBy = "concept", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<Question> questions;

    @JsonIgnore
    @OneToMany(mappedBy = "source", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Relation> sources;

    @JsonIgnore
    @OneToMany(mappedBy = "destination", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Relation> destinations;
}
