import { ITest, initTest } from './../../model/test';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';
import { TestService } from 'src/app/services/test.service';

@Component({
  selector: 'app-edit-test',
  templateUrl: './edit-test.component.html',
  styleUrls: ['./edit-test.component.css'],
})
export class EditTestComponent implements OnInit {
  test: ITest = initTest;
  id!: number;

  constructor(
    private testService: TestService,
    private questionService: QuestionService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
    this.testService.getTest(this.id).subscribe((test) => {
      this.test = test;
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
}
