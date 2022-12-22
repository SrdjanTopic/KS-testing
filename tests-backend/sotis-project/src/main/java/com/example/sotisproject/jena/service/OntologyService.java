package com.example.sotisproject.jena.service;

import com.example.sotisproject.jena.model.SotisOntologyModel;
import com.example.sotisproject.model.*;
import com.example.sotisproject.repository.*;
import lombok.AllArgsConstructor;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@AllArgsConstructor
@Service
public class OntologyService {
    private ConceptRepository conceptRepository;
    private QuestionRepository questionRepository;
    private TestRepository testRepository;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private AnswerRepository answerRepository;
    private static final String ontologyPath = new File("").getAbsolutePath() + "\\..\\sotisOntology.owl";
    private static final String stuTestPath = "C:\\Users\\Srdjan Topic\\Desktop\\SOTIS\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\stuTest.owl";
    private static final String NS = "http://www.example.org/ontology/sotis#";

    //@EventListener(ApplicationReadyEvent.class)
    public void initializeStart(){
        addStudents(studentRepository.findAll());
        addTeachers(teacherRepository.findAll());
        addConcepts(conceptRepository.findAll());
        addTests(testRepository.findAll());
        addQuestions(questionRepository.findAll());
        addAnswers(answerRepository.findAll());
    }

    public void addStudents(List<Student> students){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            students.forEach(student -> {
                Individual createdStudent = stuTestModel.createIndividual(NS + student.getFirstName()+student.getLastName(), sotisOntologyModel.getStudentClass());
                createdStudent.addLiteral(sotisOntologyModel.getId(), stuTestModel.createTypedLiteral(student.getId()));
                createdStudent.addLiteral(sotisOntologyModel.getUsername(), stuTestModel.createTypedLiteral(student.getUsername()));
                createdStudent.addLiteral(sotisOntologyModel.getUserFirstName(), stuTestModel.createTypedLiteral(student.getFirstName()));
                createdStudent.addLiteral(sotisOntologyModel.getUserLastName(), stuTestModel.createTypedLiteral(student.getLastName()));
                System.out.println("ADD STUDENT: " +  student.getFirstName()+student.getLastName());
            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTeachers(List<Teacher> teachers){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            teachers.forEach(teacher -> {
                Individual createdTeacher = stuTestModel.createIndividual(NS + teacher.getFirstName()+teacher.getLastName(), sotisOntologyModel.getTeacherClass());
                createdTeacher.addLiteral(sotisOntologyModel.getId(), stuTestModel.createTypedLiteral(teacher.getId()));
                createdTeacher.addLiteral(sotisOntologyModel.getUsername(), stuTestModel.createTypedLiteral(teacher.getUsername()));
                createdTeacher.addLiteral(sotisOntologyModel.getUserFirstName(), stuTestModel.createTypedLiteral(teacher.getFirstName()));
                createdTeacher.addLiteral(sotisOntologyModel.getUserLastName(), stuTestModel.createTypedLiteral(teacher.getLastName()));
                System.out.println("ADD TEACHER: " +  teacher.getFirstName()+teacher.getLastName());
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
                Individual createdConcept = stuTestModel.createIndividual(NS + concept.getConcept().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"), sotisOntologyModel.getConceptClass());
                createdConcept.addLiteral(sotisOntologyModel.getId(), stuTestModel.createTypedLiteral(concept.getId()));
                createdConcept.addLiteral(sotisOntologyModel.getConceptName(), stuTestModel.createTypedLiteral(concept.getConcept().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_")));
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
                Individual i = stuTestModel.getIndividual(NS + concept.getConcept().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));
                i.remove();
                System.out.println("DELETE CONCEPT: " + concept.getConcept());
            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTest(Test test){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            Individual createdTest = stuTestModel.createIndividual(NS + test.getName().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"), sotisOntologyModel.getTestClass());
            createdTest.addLiteral(sotisOntologyModel.getId(), stuTestModel.createTypedLiteral(test.getId()));
            createdTest.addLiteral(sotisOntologyModel.getTestName(), stuTestModel.createTypedLiteral(test.getName().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_")));

            System.out.println("ADD TEST: " + test.getName());
            Individual existingTeacher = stuTestModel.getIndividual(NS+test.getTeacher().getFirstName()+test.getTeacher().getLastName());
            createdTest.addProperty(sotisOntologyModel.getTestTeacher(), existingTeacher);
            System.out.println("Connected TEST " + test.getName() + " to TEACHER " + test.getTeacher().getFirstName()+test.getTeacher().getLastName());

            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTests(List<Test> tests){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            tests.forEach(test -> {
                Individual createdTest = stuTestModel.createIndividual(NS + test.getName().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"), sotisOntologyModel.getTestClass());
                createdTest.addLiteral(sotisOntologyModel.getId(), stuTestModel.createTypedLiteral(test.getId()));
                createdTest.addLiteral(sotisOntologyModel.getTestName(), stuTestModel.createTypedLiteral(test.getName().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_")));

                System.out.println("ADD TEST: " + test.getName());
                Individual existingTeacher = stuTestModel.getIndividual(NS+test.getTeacher().getFirstName()+test.getTeacher().getLastName());
                createdTest.addProperty(sotisOntologyModel.getTestTeacher(), existingTeacher);
                System.out.println("Connected TEST " + test.getName() + " to TEACHER " + test.getTeacher().getFirstName()+test.getTeacher().getLastName());

            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQuestions(List<Question> questions){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            questions.forEach(question -> {
                Individual createdQuestion = stuTestModel.createIndividual(NS + question.getQuestion().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"), sotisOntologyModel.getQuestionClass());
                createdQuestion.addLiteral(sotisOntologyModel.getId(), stuTestModel.createTypedLiteral(question.getId()));
                createdQuestion.addLiteral(sotisOntologyModel.getQuestionPoints(), stuTestModel.createTypedLiteral(question.getPoints()));
                createdQuestion.addLiteral(sotisOntologyModel.getQuestionQuestion(), stuTestModel.createTypedLiteral(question.getQuestion().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_")));
                System.out.println("ADD QUESTION: " + question.getQuestion());

                Individual existingConcept = stuTestModel.getIndividual(NS+question.getConcept().getConcept().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));
                existingConcept.addProperty(sotisOntologyModel.getConceptQuestion(), createdQuestion);
                System.out.println("Connected QUESTION " + question.getQuestion() + " to CONCEPT " + question.getConcept().getConcept());

                Individual existingTest = stuTestModel.getIndividual(NS+question.getTest().getName().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));
                existingTest.addProperty(sotisOntologyModel.getTestQuestions(), createdQuestion);
                System.out.println("Connected QUESTION " + question.getQuestion() + " to TEST " + question.getTest().getName());
            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAnswers(List<Answer> answers){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            answers.forEach(answer -> {
                Individual createdAnswer = stuTestModel.createIndividual(NS + answer.getAnswer().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"), sotisOntologyModel.getAnswerClass());
                createdAnswer.addLiteral(sotisOntologyModel.getId(), stuTestModel.createTypedLiteral(answer.getId()));
                createdAnswer.addLiteral(sotisOntologyModel.getAnswerIsCorrect(), stuTestModel.createTypedLiteral(answer.getIsCorrect()));
                System.out.println("ADD ANSWER: " + answer.getAnswer());
                answer.getStudents().forEach(student -> {
                    Individual existingStudent = stuTestModel.getIndividual(NS+student.getFirstName()+student.getLastName());
                    existingStudent.addProperty(sotisOntologyModel.getStudentAnswer(), createdAnswer);
                    System.out.println("Connected ANSWER " + answer.getAnswer() + " to STUDENT " + student.getFirstName()+student.getLastName());

                });
            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
