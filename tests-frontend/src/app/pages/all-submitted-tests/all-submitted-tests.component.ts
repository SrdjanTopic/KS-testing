import { Component, OnInit } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { StudentService } from 'src/app/services/student.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-all-submitted-tests',
  templateUrl: './all-submitted-tests.component.html',
  styleUrls: ['./all-submitted-tests.component.css'],
})
export class AllSubmittedTestsComponent implements OnInit {
  tests: any = [];
  selectedTest: { questions: any; id: any; name: any } = {
    questions: [],
    id: 0,
    name: '',
  };

  constructor(
    private studentService: StudentService,
    private userService: UserService
  ) {}

  JWTHelper: JwtHelperService = new JwtHelperService();
  token: string | null = localStorage.getItem('jwt');
  currentUser: any = this.JWTHelper.decodeToken(
    this.token ? this.token : undefined
  );

  isModalOpen: boolean = false;

  ngOnInit(): void {
    this.tests = [];
    this.loadTests();
  }

  loadTests() {
    this.studentService
      .getTestsForStudent()
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
          return accumulator + currentValue.question.points;
        },
        0
      );
      this.tests[index].myPoints = test.questions.reduce(
        (accumulator: any, currentValue: any) => {
          if (
            currentValue.question.answers.filter((a: any) => a.isCorrect)[0]
              .id === currentValue.answerId
          )
            return accumulator + currentValue.question.points;
          else return accumulator + 0;
        },
        0
      );
    });
  }

  fillTestConcepts() {
    this.tests.forEach((test: any, index: number) => {
      this.tests[index].concepts = new Set(
        test.questions.map((question: any) => question.question.concept.concept)
      );
    });
  }

  viewResults(testId: number) {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.studentService
          .getTestForStudent(user.id, testId)
          .pipe()
          .subscribe((test) => {
            this.selectedTest.name = this.tests.filter(
              (t: any) => t.id === testId
            )[0].name;
            this.selectedTest.questions = test;
            this.selectedTest.questions.forEach(
              (question: any, index: number) => {
                this.selectedTest.questions[index].questionNumber = index + 1;
              }
            );
            this.changeModalState();
          });
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
    });
  }

  changeModalState() {
    this.isModalOpen = !this.isModalOpen;
  }
}
