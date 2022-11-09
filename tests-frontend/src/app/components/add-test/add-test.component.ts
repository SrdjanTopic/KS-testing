import { TestService } from './../../services/test.service';
import { IAnswer, initAnswer } from './../../model/answer';
import { IQuestion, initQuestion } from './../../model/question';
import { ITest, initTest } from './../../model/test';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-test',
  templateUrl: './add-test.component.html',
  styleUrls: ['./add-test.component.css'],
})
export class AddTestComponent implements OnInit {
  test: ITest = initTest;
  question: IQuestion = initQuestion;
  answer: IAnswer = initAnswer;
  questionText: string = '';
  answerText: string = '';
  points: number = 0;
  questionNumber: number = 0;
  isCorrect: boolean = false;

  constructor(private testService: TestService) {}

  ngOnInit(): void {}

  addTest() {
    this.testService
      .saveTest(this.test)
      .subscribe((test) => (this.test = test));
  }

  addQuestion() {
    this.questionNumber = this.questionNumber+1;
    this.test.questions.push({
      id: 0,
      question: this.questionText,
      points: this.points,
      questionNumber: this.questionNumber,
      answers: [],
    });
  }
  
  removeQuestion(question: IQuestion){
    this.test.questions = this.test.questions.filter((q) => q !== question);
    this.test.questions.forEach(q=>{
      if(q.questionNumber>question.questionNumber){
        q.questionNumber--;
      }
    })
    this.questionNumber--;
  }

  addAnswer(question: IQuestion) {
    question.answers.push({
      id: 0,
      answer: this.answerText,
      isCorrect: this.isCorrect,
    });
  }

  removeAnswer(answer: IAnswer,question: IQuestion) {
    question.answers = question.answers.filter((a) => a !== answer);
  }
}
