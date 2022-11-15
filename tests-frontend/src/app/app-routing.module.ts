import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddTestComponent } from './components/add-test/add-test.component';
import { TestComponent } from './components/test/test.component';
import { AllTestsComponent } from './components/all-tests/all-tests.component';
import { KnowledgeGraphComponent } from './components/knowledge-graph/knowledge-graph.component';
import { LoginComponent } from './components/login/login.component';

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
    path: 'test',
    component: TestComponent,
  },
  {
    path: 'test/add',
    component: AddTestComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
