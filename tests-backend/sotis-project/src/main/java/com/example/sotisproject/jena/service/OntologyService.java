package com.example.sotisproject.jena.service;

import com.example.sotisproject.jena.model.SotisOntologyModel;
import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.repository.ConceptRepository;
import com.example.sotisproject.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@AllArgsConstructor
@Service
public class OntologyService {
    private ConceptRepository conceptRepository;
    private QuestionRepository questionRepository;

    private static final String stuTestPath = "C:\\Users\\Srdjan Topic\\Desktop\\SOTIS\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\stuTest.owl" ;
    private static final String NS = "http://www.example.org/ontology/sotis#";

//    @EventListener(ApplicationReadyEvent.class)
    public void initializeStart(){
        List<Concept> concepts = conceptRepository.findAll();
        OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
        stuTestModel.read(stuTestPath);
        SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);
        concepts.forEach(concept -> {
            Individual createdConcept = stuTestModel.createIndividual(NS + concept.getConcept().replaceAll(" ", "_"), sotisOntologyModel.getConceptClass());
            createdConcept.addLiteral(sotisOntologyModel.getNameProperty(), stuTestModel.createTypedLiteral(concept.getConcept().replaceAll(" ", "_")));
            System.out.println("ADD CONCEPT: " + concept.getConcept());
        });

        try {
            List<Question> questions = questionRepository.findAll();
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            questions.forEach(question -> {
                Individual q1 = stuTestModel.createIndividual(NS + question.getQuestion().replaceAll(" ", "_"), sotisOntologyModel.getQuestionClass());
                q1.addLiteral(sotisOntologyModel.getPointsProperty(), stuTestModel.createTypedLiteral(question.getPoints()));
                System.out.println("ADD QUESTION: " + question.getQuestion());
                Individual c1 = stuTestModel.getIndividual(NS+question.getConcept().getConcept().replaceAll(" ", "_"));
                c1.addProperty(sotisOntologyModel.getIsConceptFor(), q1);
                System.out.println("Connected QUESTION " + question.getQuestion() + " to CONCEPT " + question.getConcept().getConcept());
            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addConcepts(List<Concept> concepts){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            concepts.forEach(concept -> {
                Individual createdConcept = stuTestModel.createIndividual(NS + concept.getConcept().replaceAll(" ", "_"), sotisOntologyModel.getConceptClass());
                createdConcept.addLiteral(sotisOntologyModel.getNameProperty(), stuTestModel.createTypedLiteral(concept.getConcept().replaceAll(" ", "_")));
                System.out.println("ADD CONCEPT: " + concept.getConcept());
            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteConcepts(List<Concept> concepts){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);

            concepts.forEach(concept -> {
                Individual i = stuTestModel.getIndividual(NS + concept.getConcept().replaceAll(" ", "_"));
                i.remove();
                System.out.println("DELETE CONCEPT: " + concept.getConcept());
            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQuestion(Question question){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            Individual q1 = stuTestModel.createIndividual(NS + question.getQuestion().replaceAll(" ", "_"), sotisOntologyModel.getQuestionClass());
            q1.addLiteral(sotisOntologyModel.getPointsProperty(), stuTestModel.createTypedLiteral(question.getPoints()));
            System.out.println("ADD QUESTION: " + question.getQuestion());
            Individual c1 = stuTestModel.getIndividual(NS+question.getConcept().getConcept().replaceAll(" ", "_"));
            c1.addProperty(sotisOntologyModel.getIsConceptFor(), q1);
            System.out.println("Connected QUESTION " + question.getQuestion() + " to CONCEPT " + question.getConcept().getConcept());
            stuTestModel.write(stuTestOut, "RDF/XML");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
