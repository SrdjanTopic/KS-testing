import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthService } from '../../services/auth.service';
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
    this.fillTestPoints();
  }

  loadTests() {
    this.testService
      .getTests()
      .pipe()
      .subscribe((tests) => {
        this.tests = tests;
        this.fillTestPoints();
      });
  }

  checkStudent(): boolean {
    return this.currentUser?.roles.some(
      (role: any) => role.authority === 'ROLE_STUDENT'
    );
  }

  checkTeacher(): boolean {
    return this.currentUser?.roles.some(
      (role: any) => role.authority === 'ROLE_TEACHER'
    );
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

  openTest(testId: number) {
    this.router.navigate([`tests/${testId}`]);
  }
}
