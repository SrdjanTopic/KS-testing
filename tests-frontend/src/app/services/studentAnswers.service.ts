import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { IUser } from '../model/user';

@Injectable({
  providedIn: 'root',
})
export class StudentAnswerService {
  constructor(private http: HttpClient) {}

  getAllResultsForIITA() {
    return this.http.get(`${environment.apiUrl}/studentAnswers/allResults`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  getTestResultsForIITA(testId: Number) {
    return this.http.get(
      `${environment.apiUrl}/studentAnswers/allResults/${testId}`,
      {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
      }
    );
  }

  generateRealKS(results: any) {
    return this.http
      .post<any>(`http://localhost:5000/iita`, results)
      .pipe(tap());
  }

  getAnswersForTest(studentId: number, testId: number): Observable<any> {
    return this.http.get<any>(environment.apiUrl + `/studentAnswers/${studentId}/test/${testId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getStudentsForTest(id: number): Observable<IUser> {
    return this.http.get<IUser>(environment.apiUrl + `/studentAnswers/test/${id}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  try() {
    return this.http.get(`http://localhost:5000/`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
