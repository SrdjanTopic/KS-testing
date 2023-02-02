import { Component, OnInit } from '@angular/core';
import { ConceptService } from 'src/app/services/concept.service';
import { SparqlService } from 'src/app/services/sparql.service';
import { StudentService } from 'src/app/services/student.service';
import { TestService } from 'src/app/services/test.service';
import { UserService } from 'src/app/services/user.service';

interface Query {
  name: string;
  id: number;
}

interface ProfessionCriteria {
  conceptNames: any;
  profession: string;
}

@Component({
  selector: 'app-queries',
  templateUrl: './queries.component.html',
  styleUrls: ['./queries.component.css'],
})
export class QueriesComponent implements OnInit {
  teacherQueries: Query[] = [
    {
      name: 'Q1: Find all required concepts for learning specific concept',
      id: 1,
    },
    {
      name: 'Q2: Find all concepts for learning after knowing a specific concept',
      id: 2,
    },
    {
      name: 'Q3: Find all students that are competent for team, based on concepts',
      id: 4,
    },
    {
      name: "Q4: Find all concepts that don't exist in any of my tests",
      id: 7,
    },
    { name: 'Q5: Find all students that have finished specific test', id: 8 },
  ];

  studentQueries: Query[] = [
    {
      name: 'Q1: Find all required concepts for learning specific concept',
      id: 1,
    },
    {
      name: 'Q2: Find all concepts for learning after knowing a specific concept',
      id: 2,
    },
    {
      name: 'Q3: Find all concepts that need to be known to meet  requirements for a specific profession',
      id: 5,
    },
    {
      name: 'Q4: Find all tests to solve after learning a specific concept',
      id: 3,
    },
    { name: 'Q5: Find all my unfinished tests', id: 6 },
  ];

  user: any;

  selectedQueryId: number = 0;
  allConcepts: any = [];
  allConceptsByProfession: any = [];
  allProfessions: any = [];
  allTestsByTeacher: any = [];

  selectedConcept: any = '';
  selectedConcepts: any[] = [];
  selectedProfession: any;
  selectedTest: any;

  results: any = [];
  showResult: boolean = false;
  professionCriteria: ProfessionCriteria = {
    conceptNames: [],
    profession: '',
  };

  constructor(
    private sparqlService: SparqlService,
    private conceptService: ConceptService,
    private userService: UserService,
    private studentService: StudentService,
    private testService: TestService
  ) {}

  ngOnInit(): void {
    this.getCurrentUser();
    this.getAllProfessions();
    this.getAllConcepts();
  }

  getCurrentUser() {
    this.userService.getCurrentUser().subscribe((data) => {
      this.user = data;
      if (this.user.roles[0].authority == 'ROLE_TEACHER') {
        this.getAllTestsByTeacher();
      }
    });
  }

  getAllProfessions() {
    this.conceptService.getProfessions().subscribe((data) => {
      this.allProfessions = data;
    });
  }

  getAllConcepts() {
    this.conceptService.getConcepts().subscribe((data) => {
      this.allConcepts = data;
    });
  }

  getAllConceptsByProfession(professionId: Number) {
    this.conceptService
      .getConceptByProfession(professionId)
      .subscribe((data) => {
        this.allConceptsByProfession = data;
      });
  }

  getAllTestsByTeacher() {
    this.testService.getTestsByTeacher(this.user.id).subscribe((data) => {
      this.allTestsByTeacher = data;
    });
  }

  selectConcept(conceptName: String) {
    this.selectedConcept = conceptName;
  }

  showQuery(queryId: number) {
    this.results = [];
    this.showResult = false;
    this.selectedQueryId = queryId;
  }

  runQ1() {
    this.sparqlService.getPreviousConcepts(this.selectedConcept).subscribe(
      (data) => {
        this.results = data;
        this.showResult = true;
      },
      (error) => {
        alert('Error');
      }
    );
  }

  runQ2() {
    this.sparqlService.getDirectNextConcepts(this.selectedConcept).subscribe(
      (data) => {
        this.results = data;
        this.showResult = true;
      },
      (error) => {
        alert('Error');
      }
    );
  }

  runQ3() {
    this.sparqlService.getSolvableTests(this.selectedConcept).subscribe(
      (data) => {
        this.results = data;
        this.showResult = true;
      },
      (error) => {
        alert('Error');
      }
    );
  }

  runQ4() {
    this.sparqlService.getStudentByConcepts(this.selectedConcepts).subscribe(
      (data) => {
        this.results = data.map((row: any) => ({
          concept: row.split(':')[0],
          students: row.split(':')[1],
        }));
        this.showResult = true;
      },
      (error) => {
        alert('Error');
      }
    );
  }

  runQ5() {
    this.professionCriteria.profession = this.selectedProfession;
    this.studentService
      .getLearnedConceptsForStudent(this.user.id)
      .subscribe((data) => {
        this.professionCriteria.conceptNames = data;
        this.sparqlService
          .getConceptsByProfessionAndSkills(this.professionCriteria)
          .subscribe(
            (data) => {
              this.results = data;
              this.showResult = true;
            },
            (error) => {
              alert('Error');
            }
          );
      });
  }

  runQ6() {
    let student = this.user.firstName + this.user.lastName;
    this.sparqlService.getUnfinishedStudentTests(student).subscribe(
      (data) => {
        this.results = data;
        this.showResult = true;
      },
      (error) => {
        alert('Error');
      }
    );
  }

  runQ7() {
    let teacher = this.user.firstName + this.user.lastName;
    this.sparqlService.getUnusedConceptsByTeacher(teacher).subscribe(
      (data) => {
        this.results = data;
        this.showResult = true;
      },
      (error) => {
        alert('Error');
      }
    );
  }

  runQ8() {
    this.sparqlService.getStudentsByTeacherTest(this.selectedTest).subscribe(
      (data) => {
        this.results = data;
        this.showResult = true;
      },
      (error) => {
        alert('Error');
      }
    );
  }
}
