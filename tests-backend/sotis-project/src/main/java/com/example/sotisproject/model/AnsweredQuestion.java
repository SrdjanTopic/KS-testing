//package com.example.sotisproject.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class AnsweredQuestion {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Answer answer;
//
//    @JsonIgnore
//    @ManyToMany(mappedBy = "answeredQuestions", fetch = FetchType.EAGER)
//    private Set<Student> students;
//}
