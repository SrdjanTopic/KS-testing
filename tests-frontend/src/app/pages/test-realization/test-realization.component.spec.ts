import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestRealizationComponent } from './test-realization.component';

describe('TestRealizationComponent', () => {
  let component: TestRealizationComponent;
  let fixture: ComponentFixture<TestRealizationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestRealizationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TestRealizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
