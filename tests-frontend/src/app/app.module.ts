import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AllTestsComponent } from './components/all-tests/all-tests.component';
import { KnowledgeGraphComponent } from './components/knowledge-graph/knowledge-graph.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AllTestsComponent,
    AllTestsComponent,
    KnowledgeGraphComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
