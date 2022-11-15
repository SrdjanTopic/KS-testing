import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
