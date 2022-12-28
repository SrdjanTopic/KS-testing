package com.example.sotisproject.jena.service;

import com.example.sotisproject.jena.model.SotisOntologyModel;
import com.example.sotisproject.model.*;
import com.example.sotisproject.repository.*;
import lombok.AllArgsConstructor;
import org.apache.jena.ontology.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Service
public class OntologyService {
    private ConceptRepository conceptRepository;
    private QuestionRepository questionRepository;
    private TestRepository testRepository;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private AnswerRepository answerRepository;
    private RelationRepository relationRepository;
    private ProfessionRepository professionRepository;

    private static final String ontologyPath = new File("").getAbsolutePath() + "\\..\\sotisOntology.owl";
    
    private static final String stuTestPath = "C:\\Users\\Natalija\\OneDrive\\Documents\\Master\\Semanticki web\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\stuTest.owl";
    private static final String NS = "http://www.example.org/ontology/sotis#";

    @EventListener(ApplicationReadyEvent.class)
    public void initializeStart() {
        List<String> conceptNames = new ArrayList<>();
        conceptNames.add("HTML");
        conceptNames.add("CSS");
        AtomicReference<String> filterString = new AtomicReference<>("");
        String teacherFullName = "DarkoVrbaski";
        conceptNames.forEach(conceptName-> filterString.set(filterString + "FILTER CONTAINS(?learnedConcepts , \""+ conceptName +"\") ."));
//        System.out.println(filterString.get());
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            String queryString = "" +
                    "PREFIX ns: <http://www.example.org/ontology/sotis#> \n" +
                    "SELECT (CONCAT(?firstName, \" \", ?lastName) as ?fullName)" +
                    "WHERE" +
                    "{" +
                        "{" +
                            "SELECT ?student\n" +
                            "WHERE " +
                            "{" +
                            "?test ns:testName \""+teacherFullName+"\" ." +
                            "?test ns:testQuestions ?question ." +
                            "?question ns:questionAnswers ?answer ." +
                            "?answer ns:answerStudent ?student ." +
                            "}" +
                            "GROUP BY ?student"+
                        "}" +
                        "?student ns:userFirstName ?firstName ." +
                        "?student ns:userLastName ?lastName ." +
                    "}";

            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, stuTestModel)) {
                ResultSet results = qexec.execSelect();
                ResultSetFormatter.out(System.out, results, query);
//                while (results.hasNext()) {
//                    QuerySolution soln = results.nextSolution();
//                    Literal s = soln.getLiteral("fullName");
//                    System.out.println(s);
//                }
            }
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        addConcepts(conceptRepository.findAll());
//        addTeachers(teacherRepository.findAll());
//        addStudents(studentRepository.findAll());
//        addTests(testRepository.findAll());
//        addQuestions(questionRepository.findAll());
//        addAnswers(answerRepository.findAll());
//        addRelations(relationRepository.findAll());
//        addProfessions(professionRepository.findAll());
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
                student.getLearnedConcepts().forEach(concept -> {
                    Individual learnedConcept = stuTestModel.getIndividual(NS + concept.getConcept().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));
                    createdStudent.addProperty(sotisOntologyModel.getStudentConcept(), learnedConcept);
                });
//                student.getAnswers().forEach(answer -> {
//                    Individual answerOnt = stuTestModel.getIndividual(NS + answer.getAnswer().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));
//                    createdStudent.addProperty(sotisOntologyModel.getStudentAnswer(), answerOnt);
//                });
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
                Individual existingQuestion = stuTestModel.getIndividual(NS+answer.getQuestion().getQuestion().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));
                existingQuestion.addProperty(sotisOntologyModel.getQuestionAnswers(), createdAnswer);
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

    public void addRelations(List<Relation> relations){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            relations.forEach(relation -> {
                Individual sourceConcept = stuTestModel.getIndividual(NS+relation.getSource().getConcept().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));
                Individual destinationConcept = stuTestModel.getIndividual(NS+relation.getDestination().getConcept().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));

                sourceConcept.addProperty(sotisOntologyModel.getIsSourceFor(), destinationConcept);
                System.out.println("CONNECTED CONCEPTS: " + relation.getSource().getConcept() + "-->" + relation.getDestination().getConcept());
            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRelations(){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);

            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            List<Concept> concepts = conceptRepository.findAll();
            concepts.forEach(concept -> {
                Individual conceptInd = stuTestModel.getIndividual(NS + concept.getConcept().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));
                StmtIterator it = conceptInd.listProperties();
                it.forEach(statement -> {
                    if(statement.getPredicate().equals(sotisOntologyModel.getIsSourceFor()) || statement.getPredicate().equals(sotisOntologyModel.getIsDestinationFor())){
                        System.out.println(statement.getPredicate().toString());
                        System.out.println("DELETED CONNECTIONS for CONCEPT: " + concept.getConcept());
                    }
                });
            });

            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProfessions(List<Profession> professions){
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);

            professions.forEach(profession -> {
                Individual createdProfession = stuTestModel.createIndividual(NS + profession.getName().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"), sotisOntologyModel.getProfessionClass());
                createdProfession.addLiteral(sotisOntologyModel.getId(), stuTestModel.createTypedLiteral(profession.getId()));
                createdProfession.addLiteral(sotisOntologyModel.getProfessionName(), stuTestModel.createTypedLiteral(profession.getName().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_")));
                profession.getRequiredConcepts().forEach(concept -> {
                    Individual requiredConcept = stuTestModel.getIndividual(NS+concept.getConcept().replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_"));
                    createdProfession.addProperty(sotisOntologyModel.getRequiredConcept(), requiredConcept);
                });
                System.out.println("ADDED PROFESSION: " + profession.getName());
            });
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
