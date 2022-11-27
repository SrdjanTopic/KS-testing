import { Role } from './../../model/role.enum';
import { Component, Input, OnInit } from '@angular/core';
import { MatRadioChange } from '@angular/material/radio';
import { IAnswer, initAnswer } from 'src/app/model/answer';
import { IQuestion, initQuestion } from 'src/app/model/question';
import { ITest, initTest } from 'src/app/model/test';
import { TestService } from 'src/app/services/test.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-base-test',
  templateUrl: './base-test.component.html',
  styleUrls: ['./base-test.component.css'],
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

  constructor(
    private testService: TestService,
    private userService: UserService
  ) {}

  ngOnInit(): void {}

  addTest() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.test.teacher = user;
        this.testService
          .saveTest(this.test)
          .subscribe((test) => (this.test = test));
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
    });
  }

  submitTest() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.testService
          .submitTest(this.test)
          .subscribe((test) => (this.test = test));
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
    });
  }

  addQuestion() {
    this.questionNumber = this.questionNumber + 1;
    this.test.questions.push({
      question: this.questionText,
      points: this.points,
      questionNumber: this.questionNumber,
      answers: [],
    });
  }

  removeQuestion(question: IQuestion) {
    this.test.questions = this.test.questions.filter((q) => q !== question);
    this.test.questions.forEach((q) => {
      if (q.questionNumber > question.questionNumber) {
        q.questionNumber--;
      }
    });
    this.questionNumber--;
  }

  addAnswer() {
    this.test.questions.forEach((q) => {
      if (q.questionNumber === this.questionNumber) {
        q.answers.push({
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
    question.answers.forEach((a) => {
      if (a.answer === answer.value) {
        a.isCorrect = true;
      } else {
        a.isCorrect = false;
      }
    });
  }
}
