import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestPublishingComponent } from './test-publishing.component';

describe('TestPublishingComponent', () => {
  let component: TestPublishingComponent;
  let fixture: ComponentFixture<TestPublishingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestPublishingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TestPublishingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
