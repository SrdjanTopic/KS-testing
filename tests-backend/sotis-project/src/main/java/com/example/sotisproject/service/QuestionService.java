package com.example.sotisproject.service;

import com.example.sotisproject.jena.service.OntologyService;
import com.example.sotisproject.model.Answer;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    private AnswerService answerService;
    private OntologyService ontologyService;

    public Question addQuestion(Question question){
        Question newQuestion = questionRepository.save(new Question(null, question.getQuestion(), question.getPoints(), question.getTest(),null, question.getConcept()));
        ontologyService.addQuestion(question);
        question.getAnswers().forEach((Answer answer)->{
            answer.setQuestion(newQuestion);
            answerService.addAnswer(answer);
        });
        return newQuestion;
    }
}