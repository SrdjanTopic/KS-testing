import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { RealKSService } from 'src/app/services/realKS.service';
import { StudentAnswerService } from 'src/app/services/studentAnswers.service';
import { TestService } from 'src/app/services/test.service';
import { DataSet } from 'vis-data';
import { Network } from 'vis-network';
import { ConceptService } from '../../services/concept.service';

@Component({
  selector: 'app-real-knowledge-graph',
  templateUrl: './real-knowledge-graph.component.html',
  styleUrls: ['./real-knowledge-graph.component.css'],
})
export class RealKnowledgeGraphComponent implements OnInit {
  tests: any = [];
  selectedTest: any;
  selectedTestId: number = 0;
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
    private testService: TestService,
    private realKSService: RealKSService
  ) {}

  ngOnInit(): void {}

  async ngAfterViewInit(): Promise<void> {
    this.tests = [];
    await this.loadTests();
  }

  async draw(ksId?: number) {
    this.loadRelations(ksId);
    this.loadNodes();
    this.loadEdges();

    const data = { nodes: this.nodes, edges: this.edges };

    const container = this.visNetwork;

    const options = {
      interaction: { selectable: false, hover: false, dragNodes: false },
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
      if (this.selectedTest.realKnowledgeSpaces.length > 0) {
        const ks = this.selectedTest.realKnowledgeSpaces.filter(
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
      .createRealKSForTest(this.realKS, this.selectedTestId)
      .pipe()
      .subscribe((res) => window.location.reload());
  }

  async generateKnowledgeSpaceForTest() {
    if (this.selectedTestId === 0) return;
    this.studentAnswerService
      .getTestResultsForIITA(this.selectedTestId)
      .pipe()
      .subscribe((results: any) => {
        let minLen = results[this.concepts[0].id].length;
        for (const property in results) {
          if (results[property].length < minLen)
            minLen = results[property].length;
        }
        if (minLen < 25) {
          alert(
            `This test must have at least 25 submissions to generate a knowledge space for it! \n\nNumber of submissions: ${minLen}`
          );
          return;
        }
        let ress: any = {};
        for (const property in results) {
          ress[property] = results[property].slice(0, minLen);
        }
        this.studentAnswerService
          .generateRealKS(ress)
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
  async loadTests() {
    this.tests = await this.testService.getTests().toPromise();
    this.tests = this.tests.map((t: any, index: number) => ({
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

  async selectTest(testId: number) {
    if (this.selectedTestId === testId) {
      this.networkInstance?.selectNodes(
        this.concepts.map((concept: any) => concept.id)
      );
      this.networkInstance?.deleteSelected();
      this.selectedKS = 0;
      this.selectedTestId = 0;
      return;
    }
    this.selectedKS = 0;
    this.selectedTestId = testId;
    this.selectedTest = this.tests.filter((test: any) => test.id === testId)[0];
    this.realKS = [];
    this.isRealKSGenerated = false;
    await this.loadConcepts(testId);
    await this.draw(undefined);
  }
}
