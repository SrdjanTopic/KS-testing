import { Component, OnInit } from '@angular/core';
import { MatRadioChange } from '@angular/material/radio';
import { ActivatedRoute } from '@angular/router';
import { IAnswer } from 'src/app/model/answer';
import { IQuestion } from 'src/app/model/question';
import {
  initSubmitTestAnswers,
  ISubmitTestAnswers,
} from 'src/app/model/submitTestAnswers';
import { initTest, ITest } from 'src/app/model/test';
import { TestService } from 'src/app/services/test.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-test-realization',
  templateUrl: './test-realization.component.html',
  styleUrls: ['./test-realization.component.css'],
})
export class TestRealizationComponent implements OnInit {
  test: ITest = initTest;
  id!: number;

  submitTestAnswers: ISubmitTestAnswers = initSubmitTestAnswers;

  constructor(
    private testService: TestService,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
    this.testService.getTest(this.id).subscribe((test) => {
      this.test.name = test.name;
      this.test.teacher = test.teacher;
      this.test.questions = test.questions.map((q, index) => ({
        id: q.id,
        answers: q.answers,
        concept: q.concept,
        points: q.points,
        question: q.question,
        questionNumber: index + 1,
      }));
    });
  }

  radioChange(answer: MatRadioChange, question: IQuestion) {
    const a: IAnswer | undefined = question.answers.find(
      (ans) => ans.answer === answer.value
    );
    this.submitTestAnswers.answers = this.submitTestAnswers.answers.filter(
      (answer) => answer.questionId !== question.id
    );
    a
      ? this.submitTestAnswers.answers.push({ ...a, questionId: question.id })
      : null;
  }

  submitTest() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.submitTestAnswers.studentId = user.id;
        this.testService
          .submitTest(this.submitTestAnswers)
          .subscribe((student) => {});
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
    });
  }

  check() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {},
      error: (error) => {
        console.error('There was an error!', error);
      },
    });
  }
}
