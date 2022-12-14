import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConceptService } from 'src/app/services/concept.service';
import { RealKSService } from 'src/app/services/realKS.service';
import { RealRelationService } from 'src/app/services/realRelation.service';
import { RelationService } from 'src/app/services/relation.service';
import { StudentService } from 'src/app/services/student.service';
import { StudentAnswerService } from 'src/app/services/studentAnswers.service';
import { TestService } from 'src/app/services/test.service';
import { DataSet } from 'vis-data';
import { Network } from 'vis-network';

@Component({
  selector: 'app-student-answer-graph',
  templateUrl: './student-answer-graph.component.html',
  styleUrls: ['./student-answer-graph.component.css'],
})
export class StudentAnswerGraphComponent implements OnInit {
  id!: number;
  students: any = [];
  test: any = [];
  selectedStudent: any;
  selectedStudentId: number = 0;
  answers: any = [];
  conceptPoints: number = 0;

  concepts: any = [];
  relations: any = [];
  nodes!: DataSet<any>;
  edges!: DataSet<any>;
  startNodes!: DataSet<any>;
  startEdges!: DataSet<any>;

  conceptInputLabel: string = '';
  numberOfNodes: number = 0;

  isSelected: boolean = false;

  @ViewChild('visNetwork', { static: false }) visNetwork!: ElementRef;
  private networkInstance: Network | null = null;

  constructor(
    private relationService: RelationService,
    private conceptService: ConceptService,
    private studentAnswerService: StudentAnswerService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
  }

  async draw() {
    this.loadRelations();
    this.loadEdges();

    const data = { nodes: this.nodes, edges: this.edges };

    const container = this.visNetwork;

    const options = {
      interaction: { hover: true },
      edges: {
        arrows: 'to',
      },
    };
    this.networkInstance = new Network(container.nativeElement, data, options);
  }
  async loadStudents() {
    this.students = await this.studentAnswerService
      .getStudentsForTest(this.id)
      .toPromise();
  }

  async loadAnswers() {
    this.answers = await this.studentAnswerService
      .getAnswersForTest(this.selectedStudentId, this.id)
      .toPromise();
  }

  async ngAfterViewInit(): Promise<void> {
    await this.loadConcepts();
    await this.loadRelations();
    await this.loadStudents();
    this.loadEdges();
    const data = { nodes: this.nodes, edges: this.edges };

    const container = this.visNetwork;

    const options = {
      interaction: { hover: true },
      nodes: {
        fixed: {
          x: false,
          y: false,
        },
      },
      physics: {
        enabled: true,
        barnesHut: {
          centralGravity: 0.8,
        },
      },
      edges: {
        arrows: 'to',
      },
    };
    this.networkInstance = new Network(container.nativeElement, data, options);

    // --SELECT--
    // this.networkInstance.on('select', function (params) {
    //   console.log('select Event:', params);
    // });
  }

  async loadConcepts() {
    this.concepts = await this.conceptService.getConcepts().toPromise();
    this.numberOfNodes = this.concepts.length;
  }

  async loadRelations() {
    this.relations = await this.relationService.getRelations().toPromise();
  }

  async loadNodes() {
    this.studentAnswerService
      .getAnswersForTest(this.selectedStudentId, this.id)
      .subscribe((answers) => {
        this.concepts.forEach((concept: { concept: any; points: number }) => {
          concept.points = 0;
          answers.forEach((element: { answer: any; question: any }) => {
            if (concept.concept == element.question.concept.concept)
              element.answer.isCorrect
                ? (concept.points = +element.question.points)
                : (concept.points = -element.question.points);
          });
        });
        this.concepts.forEach((element: { concept: any; points: any }) => {
          console.log(element.points, element.concept);
        });
        this.nodes = new DataSet<any>(
          this.concepts.map((concept: any) => ({
            id: concept.id,
            label: concept.concept,
            color: concept.points > 0 ? 'aqua' : 'red',
          }))
        );
        this.startNodes = new DataSet<any>(
          this.concepts.map((concept: any) => ({
            id: concept.id,
            label: concept.concept,
            color: concept.points > 0 ? 'aqua' : 'red',
          }))
        );
        this.loadConcepts();
        this.draw();
      });
  }

  loadEdges() {
    this.edges = new DataSet<any>(
      this.relations.map((relation: any) => ({
        from: relation.source.id,
        to: relation.destination.id,
      }))
    );
    this.startEdges = new DataSet<any>(
      this.relations.map((relation: any) => ({
        from: relation.source.id,
        to: relation.destination.id,
      }))
    );
  }

  addNode() {
    this.networkInstance?.addNodeMode();
  }

  addEdge() {
    this.networkInstance?.addEdgeMode();
  }

  async selectStudent(studentId: number) {
    if (this.selectedStudentId == studentId) {
      return;
    }
    this.selectedStudentId = studentId;
    this.selectedStudent = this.students.filter(
      (student: any) => student.id === studentId
    )[0];
    await this.loadNodes();
  }
}
