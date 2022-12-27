package com.example.sotisproject.jena.model;

import lombok.Data;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;

@Data
public class SotisOntologyModel {
    private static final String NS = "http://www.example.org/ontology/sotis#";
    // classes
    private OntClass testClass;
    private OntClass questionClass;
    private OntClass conceptClass;
    private OntClass answerClass;
    private OntClass userClass;
    private OntClass teacherClass;
    private OntClass studentClass;
    private OntClass professionClass;

    // data properties
    private OntProperty id;
    private OntProperty username;
    private OntProperty userFirstName;
    private OntProperty userLastName;
    private OntProperty testName;
    private OntProperty conceptName;
    private OntProperty questionQuestion;
    private OntProperty questionPoints;
    private OntProperty answerAnswer;
    private OntProperty answerIsCorrect;
    private OntProperty professionName;

    // object properties
    private OntProperty answerQuestion;
    private OntProperty questionAnswers;

    private OntProperty answerStudent;
    private OntProperty studentAnswer;

    private OntProperty conceptStudent;
    private OntProperty studentConcept;

    private OntProperty conceptQuestion;
    private OntProperty questionConcept;

    private OntProperty questionTest;
    private OntProperty testQuestions;

    private OntProperty testTeacher;
    private OntProperty teacherTest;

    private OntProperty isSourceFor;
    private OntProperty isDestinationFor;

    private OntProperty requiredConcept;
    private OntProperty isRequiredForProfession;


    public SotisOntologyModel(OntModel ontModel){
        // classes
        testClass = ontModel.getOntClass(NS+"Test");
        questionClass = ontModel.getOntClass(NS+"Question");
        conceptClass = ontModel.getOntClass(NS+"Concept");
        answerClass = ontModel.getOntClass(NS+"Answer");
        userClass = ontModel.getOntClass(NS+"User");
        teacherClass = ontModel.getOntClass(NS+"Teacher");
        studentClass = ontModel.getOntClass(NS+"Student");
        professionClass = ontModel.getOntClass(NS+"Profession");

        // data properties
        id = ontModel.getOntProperty(NS+"id");
        username = ontModel.getOntProperty(NS+"username");
        userFirstName = ontModel.getOntProperty(NS+"userFirstName");
        userLastName = ontModel.getOntProperty(NS+"userLastName");
        testName = ontModel.getOntProperty(NS+"testName");
        conceptName = ontModel.getOntProperty(NS+"conceptName");
        questionQuestion = ontModel.getOntProperty(NS+"questionQuestion");
        questionPoints = ontModel.getOntProperty(NS+"questionPoints");
        answerAnswer = ontModel.getOntProperty(NS+"answerAnswer");
        answerIsCorrect = ontModel.getOntProperty(NS+"answerIsCorrect");
        professionName = ontModel.getOntProperty(NS+"professionName");

        // object properties
        answerQuestion = ontModel.getOntProperty(NS+"answerQuestion");
        questionAnswers = ontModel.getOntProperty(NS+"questionAnswers");

        answerStudent = ontModel.getOntProperty(NS+"answerStudent");
        studentAnswer = ontModel.getOntProperty(NS+"studentAnswer");

        studentConcept = ontModel.getOntProperty(NS+"studentConcept");
        conceptStudent = ontModel.getOntProperty(NS+"conceptStudent");

        conceptQuestion = ontModel.getOntProperty(NS+"conceptQuestion");
        questionConcept = ontModel.getOntProperty(NS+"questionConcept");

        questionTest = ontModel.getOntProperty(NS+"questionTest");
        testQuestions = ontModel.getOntProperty(NS+"testQuestions");

        testTeacher = ontModel.getOntProperty(NS+"testTeacher");
        teacherTest = ontModel.getOntProperty(NS+"teacherTest");

        isSourceFor = ontModel.getOntProperty(NS+"isSourceFor");
        isDestinationFor = ontModel.getOntProperty(NS+"isDestinationFor");

        requiredConcept = ontModel.getOntProperty(NS+"requiredConcept");
        isRequiredForProfession = ontModel.getOntProperty(NS+"isRequiredForProfession");
    }
}