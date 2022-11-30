import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { DataSet } from 'vis-data';
import { Network } from 'vis-network';
import { ConceptService } from '../../services/concept.service';
import { RelationService } from '../../services/relation.service';
import { IAnswer, initAnswer } from './../../model/answer';
import { initQuestion, IQuestion } from './../../model/question';
import { initTest, ITest } from './../../model/test';
import { TestService } from './../../services/test.service';
import { MatRadioChange } from '@angular/material/radio';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-test',
  templateUrl: './add-test.component.html',
  styleUrls: ['./add-test.component.css'],
})
export class AddTestComponent implements OnInit {
  test: ITest = initTest;
  question: IQuestion = initQuestion;
  answer: IAnswer = initAnswer;
  questionText: string = '';
  answerText: string = '';
  points: number = 0;
  questionNumber: number = 0;
  isCorrect: boolean = false;

  @ViewChild('visNetwork', { static: false }) visNetwork!: ElementRef;
  private networkInstance: Network | null = null;

  constructor(
    private testService: TestService,
    private relationService: RelationService,
    private conceptService: ConceptService,
    private userService: UserService
  ) {}

  conceptInputLabel: string = '';

  concepts: any = [];
  relations: any = [];
  nodes!: DataSet<any>;
  edges!: DataSet<any>;
  startNodes!: DataSet<any>;
  startEdges!: DataSet<any>;
  numberOfNodes: number = 0;

  isSelected: boolean = false;

  isModalOpen: boolean = false;

  selectedQuestion: number = 0;
  ngOnInit(): void {}

  async draw() {
    await this.loadConcepts();
    await this.loadRelations();
    this.loadNodes();
    this.loadEdges();

    const data = { nodes: this.nodes, edges: this.edges };

    const container = this.visNetwork;

    let selectedNode: any = null;
    let selectedEdges: any[] = [];

    const options = {
      interaction: { hover: true },
      manipulation: {
        enabled: true,
        addEdge: function (edgeData: any, callback: (arg0: any) => void) {
          if (
            data.edges.get().some((edge) => edge.from === edgeData.from) ||
            typeof edgeData.from !== 'string' ||
            edgeData.from === edgeData.to ||
            data.edges
              .get()
              .some(
                (edge) => edge.to === edgeData.to && edge.from === edgeData.from
              ) ||
            data.edges
              .get()
              .some(
                (edge) => edge.to === edgeData.from && edge.from === edgeData.to
              )
          )
            alert('You cannot connect questions with concepts in this way!');
          else {
            edgeData.arrows = {
              to: { enabled: false },
            };
            callback(edgeData);
          }
        },
        deleteEdge: function (edgeData: any, callback: (arg0: any) => void) {
          if (selectedEdges.length === 0) {
            alert('Nothing is selected!');
            return;
          }
          const edge: any = data.edges.get(edgeData.edges[0]);
          if (typeof edge.from !== 'string') {
            alert('You cannot delete edges between concepts!');
            return;
          }
          callback(edgeData);
          selectedEdges.length = 0;
        },
      },
      edges: {
        arrows: 'to',
      },
    };
    this.networkInstance = new Network(container.nativeElement, data, options);

    // --CLICKS--
    // this.networkInstance.on('click', function (this: any, params) {
    //   params.event = '[original event]';
    //   console.log(
    //     'click event, getNodeAt returns: ' + this.getNodeAt(params.pointer.DOM)
    //   );
    //   params.selectedNode = this.getNodeAt(params.pointer.DOM);
    // });
    // this.networkInstance.on('doubleClick', function (params) {
    //   params.event = '[original event]';
    // });
    // --CONTEXT
    // this.networkInstance.on('oncontext', function (params) {
    //   params.event = '[original event]';
    // });
    // --DRAG--
    // this.networkInstance.on('dragStart', function (params) {
    //   // There's no point in displaying this event on screen, it gets immediately overwritten
    //   params.event = '[original event]';
    // });
    // this.networkInstance.on('dragging', function (params) {
    //   params.event = '[original event]';
    // });
    // this.networkInstance.on('dragEnd', function (this: any, params) {
    //   params.event = '[original event]';
    //   console.log('dragEnd Event:', params);
    //   console.log(
    //     'dragEnd event, getNodeAt returns: ' +
    //       this.getNodeAt(params.pointer.DOM)
    //   );
    // });
    // this.networkInstance.on('controlNodeDragging', function (params) {
    //   params.event = '[original event]';
    // });
    // this.networkInstance.on('controlNodeDragEnd', function (params) {
    //   params.event = '[original event]';
    //   console.log('controlNodeDragEnd Event:', params);
    // });
    // --MISC--
    // this.networkInstance.on('zoom', function (params) {});
    // this.networkInstance.on('showPopup', function (params) {});
    // this.networkInstance.on('hidePopup', function () {
    //   console.log('hidePopup Event');
    // });
    // --SELECT--
    // this.networkInstance.on('select', function (params) {
    //   console.log('select Event:', params);
    // });

    this.networkInstance.on('selectNode', function (this: any, params) {
      selectedNode = data.nodes.get(this.getNodeAt(params.pointer.DOM));
      console.log('Selected NODE: ', selectedNode);
    });
    this.networkInstance.on('selectEdge', function (this: any, params) {
      selectedEdges = params.edges.map((edge: any) => data.edges.get(edge));
      console.log('Selected EDGES: ', selectedEdges);
    });
    // --DESELECT--
    this.networkInstance.on('deselectNode', function (this: any, params) {
      console.log('DEselect NODE:', selectedNode);
      selectedNode = null;
    });
    this.networkInstance.on('deselectEdge', function (this: any, params) {
      console.log('DEselect EDGES: ', selectedEdges);
      selectedEdges = [];
    });

    // --HOVER--
    // this.networkInstance.on('hoverNode', function (params) {
    //   console.log('hoverNode Event:', params);
    // });
    // this.networkInstance.on('hoverEdge', function (params) {
    //   console.log('hoverEdge Event:', params);
    // });

    // --BLUR--
    // this.networkInstance.on('blurNode', function (params) {
    //   console.log('blurNode Event:', params);
    // });
    // this.networkInstance.on('blurEdge', function (params) {
    //   console.log('blurEdge Event:', params);
    // });
  }

  async ngAfterViewInit(): Promise<void> {
    await this.draw();
  }

  async loadConcepts() {
    this.concepts = await this.conceptService.getConcepts().toPromise();
    this.numberOfNodes = this.concepts.length;
  }

  async loadRelations() {
    this.relations = await this.relationService.getRelations().toPromise();
  }

  loadNodes() {
    this.nodes = new DataSet<any>([
      ...this.concepts.map((concept: any, index: number) => ({
        id: concept.id,
        label: concept.concept,
        fixed: {
          x: true,
          y: true,
        },
      })),
      ...this.test.questions.map((question: any, index: number) => ({
        id: `Q${question.questionNumber}`,
        label: `Q${question.questionNumber}`,
        color: {
          border: '#ff0000',
          background: '#ff9999',
          hover: {
            border: '#ff0000',
            background: '#ffcccc',
          },
          highlight: {
            border: '#ff0000',
            background: '#ffcccc',
          },
        },
      })),
    ]);
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

  addEdge() {
    this.networkInstance?.addEdgeMode();
  }

  removeSelected() {
    this.networkInstance?.deleteSelected();
  }

  changeModalState() {
    if (this.test.questions.length < 1) {
      alert('Add atleast one question to the test!');
      return;
    }
    this.isModalOpen = !this.isModalOpen;
  }
  addTest() {
    this.edges.get().forEach((edge) => {
      this.test.questions.forEach((question, index: number) => {
        if (edge.from === `Q${question.questionNumber}`) {
          const node: any = this.nodes.get(edge.to);
          this.test.questions[index].concept = {
            id: node.id,
            concept: node.label,
          };
        }
      });
    });
    if (
      this.test.questions.some((question) => question.concept === undefined)
    ) {
      alert('Please connect ALL questions with concepts!');
      return;
    }
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.test.teacher = user;
        this.testService
          .saveTest(this.test)
          .subscribe((test) => (this.test = test));
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
    });
  }
  async addQuestion() {
    this.questionNumber = this.questionNumber + 1;
    this.test.questions.push({
      id: null,
      question: this.questionText,
      points: this.points,
      questionNumber: this.questionNumber,
      answers: [],
    });
    await this.draw();
  }

  removeQuestion(question: IQuestion) {
    this.test.questions = this.test.questions.filter((q) => q !== question);
    this.test.questions.forEach((q) => {
      if (q.questionNumber > question.questionNumber) {
        q.questionNumber--;
      }
    });
    this.questionNumber--;
  }

  addAnswer() {
    this.test.questions.forEach((q) => {
      if (q.questionNumber === this.questionNumber) {
        q.answers.push({
          id: null,
          answer: this.answerText,
          isCorrect: this.isCorrect,
        });
      }
    });
  }

  removeAnswer(answer: IAnswer, question: IQuestion) {
    question.answers = question.answers.filter((a) => a !== answer);
  }

  highlightQuestion(questionNumber: number) {
    this.selectedQuestion = questionNumber;
    this.networkInstance?.selectNodes([`Q${questionNumber}`]);
  }
  unhighlightQuestions() {
    this.selectedQuestion = 0;
    this.networkInstance?.selectNodes([]);
  }
  radioChange(answer: MatRadioChange, question: IQuestion) {
    question.answers.forEach((a) => {
      if (a.answer === answer.value) {
        a.isCorrect = true;
      } else {
        a.isCorrect = false;
      }
    });
  }
}
