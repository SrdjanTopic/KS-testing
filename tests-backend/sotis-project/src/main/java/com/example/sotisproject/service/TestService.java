package com.example.sotisproject.service;

import com.example.sotisproject.dto.SubmitAnswersDTO;
import com.example.sotisproject.jena.service.OntologyService;
import com.example.sotisproject.model.Answer;
import com.example.sotisproject.model.Concept;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.model.Teacher;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.repository.StudentRepository;
import com.example.sotisproject.repository.TeacherRepository;
import com.example.sotisproject.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class TestService {
    private TestRepository testRepository;
    private QuestionService questionService;
    private StudentRepository studentRepository;
    private RelationService relationService;
    private RealRelationService realRelationService;
    private TeacherRepository teacherReposiotory;
    private OntologyService ontologyService;

    public Test addTest(Test test){
        Test newTest = testRepository.save(test);
        test.getQuestions().forEach((Question question)->{
            question.setTest(newTest);
            questionService.addQuestion(question);
        });
        ontologyService.addQuestions((List<Question>) test.getQuestions());
        ontologyService.addTest(newTest);
        return newTest;
    }
    
    public Student submitTest(SubmitAnswersDTO submitAnswersDTO){
        Student student = studentRepository.findById(submitAnswersDTO.getStudentId()).get();
        List<Answer> allAnswers = student.getAnswers();
        allAnswers.addAll(submitAnswersDTO.getAnswers());
        student.setAnswers(allAnswers);
        return studentRepository.save(student);
    }

    public List<Test> getTests() {
        return testRepository.findAll();
    }
    
    public List<String> getTestsByTeacher(Long teacherId) {
    	List<String> testNames = new ArrayList<>();
    	Teacher teacher=teacherReposiotory.findById(teacherId).get();
    	List<Test> tests=testRepository.findAllByTeacherId(teacherId);
    	System.out.println(teacher.getFirstName());
    	tests.forEach(test->{
    		testNames.add(test.getName());
    	});
    	System.out.println(tests.size());
    	return testNames;
    }
    
    public Test getTest(Long id) {
        Test test = testRepository.findById(id).get();
        Set<Question> questions = new LinkedHashSet<>();
        List<Long> conceptOrder = relationService.getConceptOrder();
        conceptOrder.forEach(concept->{
            test.getQuestions().forEach(question -> {
                if (Objects.equals(question.getConcept().getId(), concept)) {
                    questions.add(question);
                }
            });
        });
        test.setQuestions(questions);
        return test;
    }

    public Test getPublishedTestById(Long testId){
        Test test = testRepository.findByIdAndTestPublicationsIsPublishedTrue(testId);
        Set<Question> questions = new LinkedHashSet<>();
        List<Long> conceptOrder;
        final Long[] ksId = new Long[1];
        test.getTestPublications().forEach(testPublication -> {
            if(testPublication.getIsPublished()) {
                ksId[0] = testPublication.getRealKnowledgeSpace().getId();
            }
        });
        if(ksId[0] ==null) conceptOrder = relationService.getConceptOrder();
        else conceptOrder = realRelationService.getConceptOrderForKnowledgeSpace(ksId[0]);

        conceptOrder.forEach(concept->{
            test.getQuestions().forEach(question -> {
                if (Objects.equals(question.getConcept().getId(), concept)) {
                    questions.add(question);
                }
            });
        });
        test.setQuestions(questions);
        return test;
    }

    public List<Test> getAllPublishedTests(){
        return testRepository.findAllByTestPublicationsIsPublishedTrue();
    }

    public List<Test> getAllUnpublishedTests() { return testRepository.findAllByTestPublicationsNull();
    }
}
