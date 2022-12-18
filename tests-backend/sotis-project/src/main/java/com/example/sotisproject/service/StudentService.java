package com.example.sotisproject.service;

import com.example.sotisproject.dto.StudentAnswerDTO;
import com.example.sotisproject.dto.StudentTestDTO;
import com.example.sotisproject.model.Answer;
import com.example.sotisproject.model.Student;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.repository.AnswerRepository;
import com.example.sotisproject.repository.StudentRepository;
import com.example.sotisproject.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {
    private StudentRepository studentRepository;
    private AnswerRepository answerRepository;
    private TestRepository testRepository;

    public Student findById(Long id){
        return studentRepository.findById(id).get();
    }

    public List<StudentAnswerDTO> findAnswersForStudent(Long id){
        List<Answer> answers = answerRepository.findAllByStudentsId(id);
        List<StudentAnswerDTO> studentAnswerDTOList = new ArrayList<>();
        answers.forEach(answer -> studentAnswerDTOList.add(new StudentAnswerDTO(answer)));
        return studentAnswerDTOList;
    }

    public List<StudentTestDTO> findTestsForStudent(Long id) {
        List<Long> testIds = new ArrayList<>();
        List<Answer> answers = answerRepository.findAllByStudentsId(id);
        List<StudentTestDTO> studentTestDTOList = new ArrayList<>();
        answers.forEach(answer -> {
            if(!testIds.contains(answer.getQuestion().getTest().getId()))
                testIds.add(answer.getQuestion().getTest().getId());
        });
        testIds.forEach(testId->{
            List<StudentAnswerDTO> studentAnswerDTOList = new ArrayList<>();
            Test test = testRepository.findById(testId).get();
            answers.forEach(answer -> {
                if(answer.getQuestion().getTest().getId()==testId)
                    studentAnswerDTOList.add(new StudentAnswerDTO(answer));
            });
            studentTestDTOList.add(new StudentTestDTO(test.getId(), test.getName(), studentAnswerDTOList));
        });


        return studentTestDTOList;
    }
    
    public List<Student> findStudentsForTest(Long id) {
        List<Student> students = studentRepository.findByTests_Id(id);
        return students;
    }

    public List<StudentAnswerDTO> getSubmittedTestForStudent(Long studentId, Long testId){
        List<Answer> answers = answerRepository.findAllByStudentsId(studentId);
        List<StudentAnswerDTO> studentAnswerDTOList = new ArrayList<>();
        answers.forEach(answer -> {
            if(answer.getQuestion().getTest().getId()==testId)
                studentAnswerDTOList.add(new StudentAnswerDTO(answer));
        });
        return studentAnswerDTOList;
    }
}
