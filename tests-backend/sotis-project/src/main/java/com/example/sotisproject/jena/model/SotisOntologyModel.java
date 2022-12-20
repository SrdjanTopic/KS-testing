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

    // data properties

    private OntProperty nameProperty; //test OR concept name
    private OntProperty textProperty; //question OR answer text
    private OntProperty pointsProperty; //question points
    private OntProperty isCorrectProperty; //answer isCorrect (true/false)

    // object properties
    private OntProperty isConceptFor; //concept isConnectedTo question
    private OntProperty consistsOf; //test consistsOf questions && question consistsOf answers


    public SotisOntologyModel(OntModel ontModel){
        testClass = ontModel.getResource(NS+"Test").as(OntClass.class);
        questionClass = ontModel.getResource(NS+"Question").as(OntClass.class);
        conceptClass = ontModel.getResource(NS+"Concept").as(OntClass.class);
        answerClass = ontModel.getResource(NS+"Answer").as(OntClass.class);

        nameProperty = ontModel.getProperty(NS+"name").as(OntProperty.class);
        textProperty = ontModel.getProperty(NS+"text").as(OntProperty.class);
        pointsProperty = ontModel.getProperty(NS+"points").as(OntProperty.class);
        isCorrectProperty = ontModel.getProperty(NS+"isCorrect").as(OntProperty.class);

        isConceptFor = ontModel.getResource(NS+"isConceptFor").as(OntProperty.class);
        consistsOf = ontModel.getResource(NS+"consistsOf").as(OntProperty.class);
    }
}