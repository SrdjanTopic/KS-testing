import { Component, Input, OnInit } from '@angular/core';
import { MatRadioChange } from '@angular/material/radio';
import { IAnswer, initAnswer } from 'src/app/model/answer';
import { IQuestion, initQuestion } from 'src/app/model/question';
import { ITest, initTest } from 'src/app/model/test';
import { TestService } from 'src/app/services/test.service';

@Component({
  selector: 'app-base-test',
  templateUrl: './base-test.component.html',
  styleUrls: ['./base-test.component.css']
})
export class BaseTestComponent implements OnInit {

  @Input() test: ITest = initTest;
  question: IQuestion = initQuestion;
  answer: IAnswer = initAnswer;
  questionText: string = '';
  answerText: string = '';
  points: number = 0;
  questionNumber: number = 0;
  isCorrect: boolean = false;

  constructor(private testService: TestService) { }

  ngOnInit(): void {
  }

  addTest() {
    this.testService
      .saveTest(this.test)
      .subscribe((test) => (this.test = test));
  }

  submitTest() {
    this.test.id = 0;
    this.testService
      .saveTest(this.test)
      .subscribe((test) => (this.test = test));
  }

  addQuestion() {
    this.questionNumber = this.questionNumber + 1;
    this.test.questions.push({
      id: 0,
      question: this.questionText,
      points: this.points,
      questionNumber: this.questionNumber,
      answers: [],
    });
  }

  removeQuestion(question: IQuestion) {
    this.test.questions = this.test.questions.filter((q) => q !== question);
    this.test.questions.forEach(q => {
      if (q.questionNumber > question.questionNumber) {
        q.questionNumber--;
      }
    })
    this.questionNumber--;
  }

  addAnswer() {
    this.test.questions.forEach(q => {
      if (q.questionNumber === this.questionNumber) {
        q.answers.push({
          id: 0,
          answer: this.answerText,
          isCorrect: this.isCorrect,
        });
      }
    });
  }

  removeAnswer(answer: IAnswer, question: IQuestion) {
    question.answers = question.answers.filter((a) => a !== answer);
  }

  radioChange(answer: MatRadioChange, question: IQuestion) {
    question.answers.forEach(a => {
      if (a.answer === answer.value) {
        a.isCorrect = true;
      }
      else {
        a.isCorrect = false;
      }
    })
  }

}
