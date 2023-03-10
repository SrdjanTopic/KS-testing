import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { IUser } from '../model/user';

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  constructor(private http: HttpClient) {}

  getStudentsForTest(id: number): Observable<IUser> {
    return this.http
      .get<IUser>(environment.apiUrl + `/students/test/${id}`)
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }

  getTestsForStudent() {
    return this.http.get(`${environment.apiUrl}/students/tests/`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  getLearnedConceptsForStudent(userId: number) {
    return this.http.get(
      `${environment.apiUrl}/students/${userId}/learnedConcepts`,
      {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
      }
    );
  }

  getTestForStudent(userId: number, testId: number) {
    return this.http.get(
      `${environment.apiUrl}/students/${userId}/tests/${testId}`,
      {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
      }
    );
  }



}
