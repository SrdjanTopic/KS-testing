import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentAnswerGraphComponent } from './student-answer-graph.component';

describe('StudentAnswerGraphComponent', () => {
  let component: StudentAnswerGraphComponent;
  let fixture: ComponentFixture<StudentAnswerGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentAnswerGraphComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentAnswerGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
