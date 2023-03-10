import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { ITest } from './../model/test';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { ISubmitTestAnswers } from '../model/submitTestAnswers';

@Injectable({
  providedIn: 'root',
})
export class TestService {
  constructor(private http: HttpClient) {}

  getTests() {
    return this.http.get(`${environment.apiUrl}/tests/`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  getPublishedTests() {
    return this.http.get(`${environment.apiUrl}/tests/published`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  getUnpublishedTests() {
    return this.http.get(`${environment.apiUrl}/tests/unpublished`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  getTestsByTeacher(teacherId:Number) {
    return this.http.get(`${environment.apiUrl}/tests/${teacherId}`+`/teacher`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  saveTest(test: ITest): Observable<ITest> {
    return this.http.post<ITest>(environment.apiUrl + '/tests/add', test).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  submitTest(test: ISubmitTestAnswers): Observable<ITest> {
    return this.http
      .post<ITest>(environment.apiUrl + '/tests/submit', test)
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getTest(id: number): Observable<ITest> {
    return this.http.get<ITest>(environment.apiUrl + `/tests/${id}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
