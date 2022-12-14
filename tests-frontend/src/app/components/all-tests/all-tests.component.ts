import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { TestService } from '../../services/test.service';

@Component({
  selector: 'app-all-tests',
  templateUrl: './all-tests.component.html',
  styleUrls: ['./all-tests.component.css'],
})
export class AllTestsComponent implements OnInit {
  tests: any = [];

  constructor(private testService: TestService, private router: Router) {}

  JWTHelper: JwtHelperService = new JwtHelperService();
  token: string | null = localStorage.getItem('jwt');
  currentUser: any = this.JWTHelper.decodeToken(
    this.token ? this.token : undefined
  );

  ngOnInit(): void {
    this.tests = [];
    this.loadTests();
  }

  loadTests() {
    if (
      this.currentUser &&
      this.currentUser.roles.some(
        (role: any) => role.authority === 'ROLE_TEACHER'
      )
    )
      this.testService
        .getTests()
        .pipe()
        .subscribe((tests) => {
          this.tests = tests;
          this.fillTestPoints();
          this.fillTestConcepts();
        });
    else
      this.testService
        .getPublishedTests()
        .pipe()
        .subscribe((tests) => {
          this.tests = tests;
          this.fillTestPoints();
          this.fillTestConcepts();
        });
  }

  fillTestPoints() {
    this.tests.forEach((test: any, index: number) => {
      this.tests[index].maxPoints = test.questions.reduce(
        (accumulator: any, currentValue: any) => {
          return accumulator + currentValue.points;
        },
        0
      );
    });
  }

  fillTestConcepts() {
    this.tests.forEach((test: any, index: number) => {
      this.tests[index].concepts = new Set(
        test.questions.map((question: any) => question.concept.concept)
      );
    });
  }
}
