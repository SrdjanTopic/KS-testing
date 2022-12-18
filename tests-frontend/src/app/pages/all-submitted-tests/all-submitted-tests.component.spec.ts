import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllSubmittedTestsComponent } from './all-submitted-tests.component';

describe('AllSubmittedTestsComponent', () => {
  let component: AllSubmittedTestsComponent;
  let fixture: ComponentFixture<AllSubmittedTestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllSubmittedTestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllSubmittedTestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
