import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddTestComponent } from './components/add-test/add-test.component';
import { TestComponent } from './components/test/test.component';
import { AllTestsComponent } from './components/all-tests/all-tests.component';
import { KnowledgeGraphComponent } from './components/knowledge-graph/knowledge-graph.component';
import { LoginComponent } from './components/login/login.component';
import { TestRealizationComponent } from './pages/test-realization/test-realization.component';
import { RealKnowledgeGraphComponent } from './components/real-knowledge-graph/real-knowledge-graph.component';
import { TestPublishingComponent } from './components/test-publishing/test-publishing.component';
import { StudentAnswerGraphComponent } from './pages/student-answer-graph/student-answer-graph.component';
import { AllSubmittedTestsComponent } from './pages/all-submitted-tests/all-submitted-tests.component';
import { QueriesComponent } from './components/queries/queries.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'tests',
    component: AllTestsComponent,
  },
  // {
  //   path: 'tests/:id',
  //   component: AllTestsComponent,
  // },
  {
    path: 'graph',
    component: KnowledgeGraphComponent,
  },
  {
    path: 'realGraph',
    component: RealKnowledgeGraphComponent,
  },
  {
    path: 'test',
    component: TestComponent,
  },
  {
    path: 'test/add',
    component: AddTestComponent,
  },
  {
    path: 'test/:id/submit',
    component: TestRealizationComponent,
  },
  {
    path: 'tests/publishing',
    component: TestPublishingComponent,
  },
  {
    path: 'tests/queries',
    component: QueriesComponent,
  },
  {
    path: 'test/:id/answers/graph',
    component: StudentAnswerGraphComponent,
  },
  {
    path: 'myTests',
    component: AllSubmittedTestsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
