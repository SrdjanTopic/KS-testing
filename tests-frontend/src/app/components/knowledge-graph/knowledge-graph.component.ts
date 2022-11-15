import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
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
  concepts: any = [];
  relations: any = [];
  nodes: any;
  edges: any;

  @ViewChild('visNetwork', { static: false }) visNetwork!: ElementRef;
  private networkInstance: any;

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
    this.networkInstance = new Network(container.nativeElement, data, {
      edges: { arrows: { to: { enabled: true, type: 'arrow' } } },
    });
  }

  async loadConcepts() {
    this.concepts = await this.conceptService.getConcepts().toPromise();
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
  }
  loadEdges() {
    this.edges = new DataSet<any>(
      this.relations.map((relation: any) => ({
        from: relation.source.id,
        to: relation.destination.id,
      }))
    );
  }
  addNode() {
    console.log(this.concepts);
  }
}
