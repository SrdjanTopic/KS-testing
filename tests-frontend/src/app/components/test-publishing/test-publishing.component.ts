import { Component, OnInit } from '@angular/core';
import { TestService } from 'src/app/services/test.service';
import { TestPublicationService } from 'src/app/services/testPublication.service';

@Component({
  selector: 'app-test-publishing',
  templateUrl: './test-publishing.component.html',
  styleUrls: ['./test-publishing.component.css'],
})
export class TestPublishingComponent implements OnInit {
  constructor(
    private testService: TestService,
    private testPublishingService: TestPublicationService
  ) {}

  tests: any = [];
  selectedTestId: number = 0;
  selectedTest: any;
  selectedRksId: number = 0;
  selectedPublicationId: number = 0;

  isModalOpen: Boolean = false;
  modalKnowledgeSpaces: any = [];

  async ngOnInit(): Promise<void> {
    this.tests = [];
    await this.loadTests();
  }

  async loadTests() {
    this.tests = await this.testService.getTests().toPromise();
    this.tests = this.tests.map((t: any, index: number) => ({
      ...t,
      testNum: index + 1,
      testPublications: t.testPublications.map((pub: any, i: number) => ({
        ...pub,
        pubNum: i + 1,
        publicationDate: pub.publicationDate
          .replace('T', ' ')
          .replace('-', '.')
          .replace('-', '.')
          .slice(0, 16),
      })),
      publication: t.testPublications.filter((pub: any) => pub.isPublished)[0],
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

    this.fillTestPoints();
    this.fillTestConcepts();
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
      this.tests[index].testNum = index + 1;
      this.tests[index].concepts = new Set(
        test.questions.map((question: any) => question.concept.concept)
      );
    });
  }

  changeModalState() {
    this.isModalOpen = !this.isModalOpen;
    this.selectedRksId = 0;
  }

  openPublishModal(testId: number) {
    this.selectedTestId = testId;
    this.selectedTest = this.tests.filter((test: any) => test.id === testId)[0];
    this.changeModalState();
  }

  selectRealKS(rksId: number) {
    if (this.selectedRksId === rksId) this.selectedRksId = 0;
    else this.selectedRksId = rksId;
  }

  async publishNewVersionOfTest() {
    const res = await this.testPublishingService
      .publishNewVersionOfTest({
        testId: this.selectedTestId,
        rksId: this.selectedRksId === 0 ? null : this.selectedRksId,
      })
      .toPromise();
    window.location.reload();
  }

  onSelectChange(event: any) {
    this.selectedPublicationId = parseInt(event.target.value);
  }

  async republishPreviousVersionOfTest() {
    if (this.selectedPublicationId === 0) return;
    const res = await this.testPublishingService
      .rePublishOldVersionOfTest(this.selectedPublicationId)
      .toPromise();
    window.location.reload();
  }
}
