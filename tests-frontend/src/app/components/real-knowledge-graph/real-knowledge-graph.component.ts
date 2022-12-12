import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { RealRelationService } from 'src/app/services/realRelation.service';
import { StudentAnswerService } from 'src/app/services/studentAnswers.service';
import { TestService } from 'src/app/services/test.service';
import { DataSet } from 'vis-data';
import { Network } from 'vis-network';
import { ConceptService } from '../../services/concept.service';
import { RelationService } from '../../services/relation.service';

@Component({
  selector: 'app-real-knowledge-graph',
  templateUrl: './real-knowledge-graph.component.html',
  styleUrls: ['./real-knowledge-graph.component.css'],
})
export class RealKnowledgeGraphComponent implements OnInit {
  tests: any = [];

  realKS: any = [];
  isRealKSGenerated: boolean = false;

  concepts: any = [];
  relations: any = [];
  nodes!: DataSet<any>;
  edges!: DataSet<any>;
  startNodes!: DataSet<any>;
  startEdges!: DataSet<any>;

  selectedTestId: number = 0;

  @ViewChild('visNetwork', { static: false }) visNetwork!: ElementRef;
  private networkInstance: Network | null = null;

  constructor(
    private conceptService: ConceptService,
    private studentAnswerService: StudentAnswerService,
    private realRelationService: RealRelationService,
    private testService: TestService
  ) {}

  ngOnInit(): void {}

  async ngAfterViewInit(): Promise<void> {
    this.tests = [];
    await this.loadTests();
  }

  async draw(testId?: number) {
    await this.loadConcepts(testId);
    await this.loadRelations(testId);
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

  async loadRelations(testId?: number) {
    if (testId)
      this.relations = await this.realRelationService
        .getRealRelationsForTest(testId)
        .toPromise();
    else {
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
    this.realRelationService
      .saveRealRelationsForTest(this.realKS, this.selectedTestId)
      .pipe()
      .subscribe((res) => console.log(res));
    window.location.reload();
  }

  async generateKnowledgeSpaceForTest() {
    if (this.selectedTestId === 0) return;
    this.studentAnswerService
      .getTestResultsForIITA(this.selectedTestId)
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
            this.realKS = res.implications.map((implication: any) => ({
              sourceId: implication[0] + 1,
              destinationId: implication[1] + 1,
            }));
            await this.draw(undefined);
            this.isRealKSGenerated = true;
          });
      });
  }
  async loadTests() {
    this.tests = await this.testService.getTests().toPromise();
    this.tests = this.tests.map((t: any, index: number) => ({
      ...t,
      testNumber: index + 1,
    }));
  }

  async drawForTest(testId: number) {
    if (this.selectedTestId === testId) return;
    this.selectedTestId = testId;
    this.realKS = [];
    this.isRealKSGenerated = false;
    await this.draw(testId);
  }
}
