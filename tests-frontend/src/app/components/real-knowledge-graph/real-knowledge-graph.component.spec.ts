import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RealKnowledgeGraphComponent } from './real-knowledge-graph.component';

describe('RealKnowledgeGraphComponent', () => {
  let component: RealKnowledgeGraphComponent;
  let fixture: ComponentFixture<RealKnowledgeGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RealKnowledgeGraphComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RealKnowledgeGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
