import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtInterceptor } from './interceptor/jwt.interceptor';
import { ErrorInterceptor } from './interceptor/error.interceptor';
import { HeaderComponent } from './components/header/header.component';
import { TestComponent } from './components/test/test.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { AddTestComponent } from './components/add-test/add-test.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { HasRoleDirective } from './directive/hasRole.directive';
import { AllTestsComponent } from './components/all-tests/all-tests.component';
import { KnowledgeGraphComponent } from './components/knowledge-graph/knowledge-graph.component';
import { MatRadioModule } from '@angular/material/radio';
import { BaseTestComponent } from './components/base-test/base-test.component';
import { TestRealizationComponent } from './pages/test-realization/test-realization.component';
import { RealKnowledgeGraphComponent } from './components/real-knowledge-graph/real-knowledge-graph.component';
import { TestPublishingComponent } from './components/test-publishing/test-publishing.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AllTestsComponent,
    AllTestsComponent,
    KnowledgeGraphComponent,
    HeaderComponent,
    TestComponent,
    AddTestComponent,
    HasRoleDirective,
    BaseTestComponent,
    TestRealizationComponent,
    RealKnowledgeGraphComponent,
    TestPublishingComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatButtonModule,
    NgbModule,
    MatCheckboxModule,
    MatInputModule,
    HttpClientModule,
    MatRadioModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
