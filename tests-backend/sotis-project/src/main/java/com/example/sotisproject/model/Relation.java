package com.example.sotisproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "source_id")
    private Concept source;

    @ManyToOne(fetch = FetchType.EAGER)
    private Concept destination;

}
