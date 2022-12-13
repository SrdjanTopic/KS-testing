import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConceptService } from 'src/app/services/concept.service';
import { RealKSService } from 'src/app/services/realKS.service';
import { StudentService } from 'src/app/services/student.service';
import { StudentAnswerService } from 'src/app/services/studentAnswers.service';
import { DataSet } from 'vis-data';
import { Network } from 'vis-network';

@Component({
  selector: 'app-student-answer-graph',
  templateUrl: './student-answer-graph.component.html',
  styleUrls: ['./student-answer-graph.component.css']
})
export class StudentAnswerGraphComponent implements OnInit {

  id!: number;
  students: any = [];
  selectedStudent: any;
  selectedStudentId: number = 0;
  selectedKS: number = 0;

  realKS: any = [];
  isRealKSGenerated: boolean = false;

  concepts: any = [];
  relations: any = [];
  nodes!: DataSet<any>;
  edges!: DataSet<any>;
  startNodes!: DataSet<any>;
  startEdges!: DataSet<any>;

  @ViewChild('visNetwork', { static: false }) visNetwork!: ElementRef;
  private networkInstance: Network | null = null;

  constructor(
    private conceptService: ConceptService,
    private studentAnswerService: StudentAnswerService,
    private studentService: StudentService,
    private realKSService: RealKSService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
  }

  async ngAfterViewInit(): Promise<void> {
    this.students = [];
    await this.loadStudents();
  }

  async draw(ksId?: number) {
    this.loadRelations(ksId);
    this.loadNodes();
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

  async loadConcepts(testId?: number) {
    if (testId)
      this.concepts = await this.conceptService
        .getConceptsForTest(testId)
        .toPromise();
  }
  loadRelations(ksId?: number) {
    if (ksId) {
      if (this.selectedStudent.realKnowledgeSpaces.length > 0) {
        const ks = this.selectedStudent.realKnowledgeSpaces.filter(
          (rks: any) => rks.id === ksId
        )[0];
        this.relations = ks.relations;
      } else this.relations = [];
    } else {
      this.relations = this.realKS.map((rks: any) => ({
        realSource: { id: rks.sourceId },
        realDestination: { id: rks.destinationId },
      }));
    }
  }

  loadNodes() {
    this.nodes = new DataSet<any>(
      this.concepts.map((concept: any) => ({
        id: concept.id,
        label: concept.concept,
      }))
    );
    this.startNodes = new DataSet<any>(
      this.concepts.map((concept: any) => ({
        id: concept.id,
        label: concept.concept,
      }))
    );
  }

  loadEdges() {
    this.edges = new DataSet<any>(
      this.relations.map((relation: any) => ({
        from: relation.realSource.id,
        to: relation.realDestination.id,
      }))
    );
    this.startEdges = new DataSet<any>(
      this.relations.map((relation: any) => ({
        from: relation.realSource.id,
        to: relation.realDestination.id,
      }))
    );
  }

  async saveRealKS() {
    this.realKSService
      .createRealKSForTest(this.realKS, this.selectedStudentId)
      .pipe()
      .subscribe((res) => window.location.reload());
  }

  async generateKnowledgeSpaceForStudent() {
    if (this.selectedStudentId === 0) return;
    this.studentAnswerService
      .getTestResultsForIITA(this.selectedStudentId)
      .pipe()
      .subscribe((results: any) => {
        if (results[this.concepts[0].id].length < 25) {
          alert(
            `This test must have at least 25 submissions to generate a knowledge space for it! \n\nNumber of submissions: ${
              results[this.concepts[0].id].length
            }`
          );
          return;
        }
        this.studentAnswerService
          .generateRealKS(results)
          .subscribe(async (res) => {
            const concepts = res.concepts.map((c: any) => parseInt(c));
            const implications = res.implications.map((implication: any) =>
              implication.map((i: any) => concepts[i])
            );
            this.realKS = implications.map((implication: any) => ({
              sourceId: implication[0],
              destinationId: implication[1],
            }));
            await this.draw(undefined);
            this.selectedKS = 0;
            this.isRealKSGenerated = true;
          });
      });
  }
  async loadStudents() {
    this.students = await this.studentAnswerService.getStudentsForTest(this.id).toPromise();
    this.students = this.students.map((t: any, index: number) => ({
      ...t,
      testNumber: index + 1,
      realKnowledgeSpaces: t.realKnowledgeSpaces.map((rks: any, i: number) => ({
        ...rks,
        rksNum: i + 1,
        creationDate: rks.creationDate
          .replace('T', ' ')
          .replace('-', '.')
          .replace('-', '.')
          .slice(0, 16),
      })),
    }));
  }

  async drawForKS(ksId: number) {
    if (this.selectedKS === ksId) {
      this.selectedKS = 0;
      await this.draw(undefined);
      return;
    }
    this.selectedKS = ksId;
    await this.draw(ksId);
  }

  async selectStudent(testId: number) {
    if (this.selectedStudentId === testId) {
      this.selectedKS = 0;
      this.selectedStudentId = 0;
      return;
    }
    this.selectedKS = 0;
    this.selectedStudentId = testId;
    this.selectedStudent = this.students.filter((test: any) => test.id === testId)[0];
    this.realKS = [];
    this.isRealKSGenerated = false;
    await this.loadConcepts(testId);
    await this.draw(undefined);
  }

}
