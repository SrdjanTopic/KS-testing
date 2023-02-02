import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { initTest, ITest } from 'src/app/model/test';
import { QuestionService } from 'src/app/services/question.service';
import { TestService } from 'src/app/services/test.service';
import { environment } from 'src/environments/environment';
@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css'],
})
export class TestComponent implements OnInit {
  constructor(
    private testService: TestService,
    private questionService: QuestionService,
    private route: ActivatedRoute
  ) {}

  showFiller = true;
  test: any;
  id!: number;
  url = environment.apiUrl;
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

  generateQTI(question: any) {
    this.questionService.qti(question.question.id).subscribe();
  }
}
