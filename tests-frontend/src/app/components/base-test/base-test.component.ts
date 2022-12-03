import { IUser, initUser, IRole } from './../../model/user';
import { Role } from './../../model/role.enum';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatRadioChange } from '@angular/material/radio';
import { IAnswer, initAnswer } from 'src/app/model/answer';
import { IQuestion, initQuestion } from 'src/app/model/question';
import { ITest, initTest } from 'src/app/model/test';
import { TestService } from 'src/app/services/test.service';
import { UserService } from 'src/app/services/user.service';
import {
  initSubmitTestAnswers,
  ISubmitTestAnswers,
} from 'src/app/model/submitTestAnswers';

@Component({
  selector: 'app-base-test',
  templateUrl: './base-test.component.html',
  styleUrls: ['./base-test.component.css'],
})
export class BaseTestComponent implements OnInit {
  @Input() test: ITest = initTest;
  user: IUser = initUser;
  question: IQuestion = initQuestion;
  answer: IAnswer = initAnswer;
  questionText: string = '';
  answerText: string = '';
  points: number = 0;
  @Input() questionNumber: number = 0;
  @Output() numberChanged = new EventEmitter<number>();
  isCorrect: boolean = false;
  id!: number;
  submitTestAnswers: ISubmitTestAnswers = initSubmitTestAnswers;

  constructor(
    private testService: TestService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.user = user;
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
    });
  }

  changeNumber() {
    this.numberChanged.emit(this.questionNumber);
  }

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

  removeQuestion(question: IQuestion) {
    this.test.questions = this.test.questions.filter((q) => q !== question);
    this.test.questions.forEach((q) => {
      if (q.questionNumber > question.questionNumber) {
        q.questionNumber--;
      }
    });
    this.questionNumber--;
    this.changeNumber();
    console.log(this.questionNumber);
  }

  removeAnswer(answer: IAnswer, question: IQuestion) {
    question.answers = question.answers.filter((a) => a !== answer);
  }

  radioChange(answer: MatRadioChange, question: IQuestion) {
    if (this.user.roles[0].authority=='ROLE_STUDENT') {
      const a: IAnswer | undefined = question.answers.find(
        (ans) => ans.answer === answer.value
      );
      this.submitTestAnswers.answers = this.submitTestAnswers.answers.filter(
        (answer) => answer.questionId !== question.id
      );
      a
        ? this.submitTestAnswers.answers.push({ ...a, questionId: question.id })
        : null;
    } else if (this.user.roles[0].authority=='ROLE_TEACHER') {
      question.answers.forEach((a) => {
        if (a.answer === answer.value) {
          a.isCorrect = true;
        } else {
          a.isCorrect = false;
        }
      });
    }
  }

  submitTest() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.submitTestAnswers.studentId = user.id;
        this.testService
          .submitTest(this.submitTestAnswers)
          .subscribe((student) => console.log(student));
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
    });
  }

  check() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        console.log(user);
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
    });
  }
}
