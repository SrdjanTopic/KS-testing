import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { DataSet } from 'vis-data';
import { data, Network } from 'vis-network';
import { ConceptService } from '../../services/concept.service';
import { RelationService } from '../../services/relation.service';

@Component({
  selector: 'app-knowledge-graph',
  templateUrl: './knowledge-graph.component.html',
  styleUrls: ['./knowledge-graph.component.css'],
})
export class KnowledgeGraphComponent implements OnInit {
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
    private conceptService: ConceptService
  ) {}

  ngOnInit(): void {}

  async ngAfterViewInit(): Promise<void> {
    await this.loadConcepts();
    await this.loadRelations();
    this.loadNodes();
    this.loadEdges();

    const data = { nodes: this.nodes, edges: this.edges };

    const container = this.visNetwork;

    let selectedNode: any = null;
    let selectedEdges: any[] = [];
    // let deletedNode: any = null;
    // let deletedEdges: any[] = [];
    // let addedNode: any = null;
    // let addedEdges: any[] = [];

    const options = {
      interaction: { hover: true },
      manipulation: {
        enabled: true,
        addNode: function (nodeData: any, callback: (arg0: any) => void) {
          nodeData.label = (<HTMLInputElement>(
            document.getElementById('label')
          )).value;

          nodeData.id = data.nodes.get()[data.nodes.get().length - 1].id + 1;
          // nodeData.color = {
          //   border: '#000000',
          //   background: '#000000',
          //   highlight: {
          //     border: '#2B7CE9',
          //     background: '#D2E5FF'
          //   }
          // }
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
  }

  async saveGraph() {
    const deletionConcepts = this.startNodes
      .get()
      .filter((sn) => !this.nodes.get().some((n) => sn.id === n.id));
    console.log(deletionConcepts);
    const additionConcepts = this.nodes
      .get()
      .filter((n) => !this.startNodes.get().some((sn) => sn.id === n.id));
    console.log(deletionConcepts, additionConcepts);
    if (deletionConcepts.length > 0)
      this.conceptService.deleteConcepts(
        deletionConcepts
          .filter((node, index: number) => index >= this.numberOfNodes)
          .map((node) => ({ id: node.id, concept: node.label }))
      );
    if (additionConcepts.length > 0)
      await this.conceptService
        .addConcepts(
          additionConcepts
            .filter((node, index: number) => index >= this.numberOfNodes)
            .map((node) => ({ id: node.id, concept: node.label }))
        )
        .toPromise();

    await this.relationService
      .updateRelations(
        this.edges.get().map((relation) => ({
          source: this.nodes.get(relation.from),
          destination: this.nodes.get(relation.to),
        }))
      )
      .toPromise();
    window.location.reload();
  }
}
