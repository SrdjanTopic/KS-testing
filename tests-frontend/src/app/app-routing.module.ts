import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllTestsComponent } from './all-tests/all-tests.component';
import { KnowledgeGraphComponent } from './knowledge-graph/knowledge-graph.component';
import { AddTestComponent } from './components/add-test/add-test.component';
import { TestComponent } from './components/test/test.component';
import { LoginComponent } from './login/login.component';

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
