import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { RealRelationService } from 'src/app/services/realRelation.service';
import { StudentAnswerService } from 'src/app/services/studentAnswers.service';
import { DataSet } from 'vis-data';
import { Network } from 'vis-network';
import { ConceptService } from '../../services/concept.service';
import { RelationService } from '../../services/relation.service';

@Component({
  selector: 'app-knowledge-graph',
  templateUrl: './knowledge-graph.component.html',
  styleUrls: ['./knowledge-graph.component.css'],
})
export class KnowledgeGraphComponent implements OnInit {
  realKS: any = [];

  conceptInputLabel: string = '';

  concepts: any = [];
  relations: any = [];
  nodes!: DataSet<any>;
  edges!: DataSet<any>;
  startNodes!: DataSet<any>;
  startEdges!: DataSet<any>;
  numberOfNodes: number = 0;

  isSelected: boolean = false;

  @ViewChild('visNetwork', { static: false }) visNetwork!: ElementRef;
  private networkInstance: Network | null = null;

  constructor(
    private relationService: RelationService,
    private conceptService: ConceptService,
    private studentAnswerService: StudentAnswerService,
    private realRelationService: RealRelationService
  ) {}

  ngOnInit(): void {}

  async ngAfterViewInit(): Promise<void> {
    (<HTMLInputElement>(
      document.getElementById('removeSelectionBtn')
    )).classList.add('disabledBtn');
    (<HTMLInputElement>document.getElementById('removeSelectionBtn')).disabled =
      true;
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
      manipulation: {
        enabled: true,
        addNode: function (nodeData: any, callback: (arg0: any) => void) {
          nodeData.label = (<HTMLInputElement>(
            document.getElementById('label')
          )).value;
          nodeData.id = data.nodes.get()[data.nodes.get().length - 1].id + 1;
          nodeData.x = -100;
          nodeData.y = 100;
          callback(nodeData);
        },
        addEdge: function (edgeData: any, callback: (arg0: any) => void) {
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
            ? alert('You cannot connect concepts in this way!')
            : callback(edgeData);
        },
        deleteNode: function (data: any, callback: (arg0: any) => void) {
          selectedNode ? callback(data) : alert('Nothing is selected!');
          selectedNode = null;
        },
        deleteEdge: function (data: any, callback: (arg0: any) => void) {
          selectedEdges.length > 0
            ? callback(data)
            : alert('Nothing is selected!');
          selectedEdges.length = 0;
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

    this.networkInstance.on('selectNode', function (this: any, params) {
      selectedNode = data.nodes.get(this.getNodeAt(params.pointer.DOM));
      (<HTMLInputElement>(
        document.getElementById('removeSelectionBtn')
      )).disabled = false;
      (<HTMLInputElement>(
        document.getElementById('removeSelectionBtn')
      )).classList.remove('disabledBtn');
      console.log('Selected NODE: ', selectedNode);
    });
    this.networkInstance.on('selectEdge', function (this: any, params) {
      selectedEdges = params.edges.map((edge: any) => data.edges.get(edge));
      (<HTMLInputElement>(
        document.getElementById('removeSelectionBtn')
      )).disabled = false;
      (<HTMLInputElement>(
        document.getElementById('removeSelectionBtn')
      )).classList.remove('disabledBtn');
      console.log('Selected EDGES: ', selectedEdges);
    });
    // --DESELECT--
    this.networkInstance.on('deselectNode', function (this: any, params) {
      (<HTMLInputElement>(
        document.getElementById('removeSelectionBtn')
      )).disabled = true;
      (<HTMLInputElement>(
        document.getElementById('removeSelectionBtn')
      )).classList.add('disabledBtn');
      console.log('DEselect NODE:', selectedNode);
      selectedNode = null;
    });
    this.networkInstance.on('deselectEdge', function (this: any, params) {
      (<HTMLInputElement>(
        document.getElementById('removeSelectionBtn')
      )).disabled = true;
      (<HTMLInputElement>(
        document.getElementById('removeSelectionBtn')
      )).classList.add('disabledBtn');
      console.log('DEselect EDGES: ', selectedEdges);
      selectedEdges = [];
    });
  }

  async loadConcepts() {
    this.concepts = await this.conceptService.getConcepts().toPromise();
    this.numberOfNodes = this.concepts.length;
  }

  async loadRelations() {
    this.relations = await this.relationService.getRelations().toPromise();
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

  removeSelected() {
    this.networkInstance?.deleteSelected();
    (<HTMLInputElement>(
      document.getElementById('removeSelectionBtn')
    )).classList.add('disabledBtn');
    (<HTMLInputElement>document.getElementById('removeSelectionBtn')).disabled =
      true;
  }

  async saveGraph() {
    const deletionConcepts = this.startNodes
      .get()
      .filter((sn) => !this.nodes.get().some((n) => sn.id === n.id));
    const additionConcepts = this.nodes
      .get()
      .filter((n) => !this.startNodes.get().some((sn) => sn.id === n.id));
    console.log('Add concepts', additionConcepts);
    console.log('Delete concepts', deletionConcepts);
    if (deletionConcepts.length > 0)
      await this.conceptService
        .deleteConcepts(
          deletionConcepts.map((node) => ({ id: node.id, concept: node.label }))
        )
        .toPromise();
    if (additionConcepts.length > 0)
      await this.conceptService
        .addConcepts(
          additionConcepts.map((node) => ({ id: node.id, concept: node.label }))
        )
        .toPromise();

    this.relationService
      .updateRelations(
        this.edges.get().map((relation) => ({
          source: this.nodes.get(relation.from),
          destination: this.nodes.get(relation.to),
        }))
      )
      .subscribe((res) => window.location.reload());
  }
}
