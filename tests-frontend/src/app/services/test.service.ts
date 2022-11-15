import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ITest } from './../model/test';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TestService {
  constructor(private http: HttpClient) {}

  getTests() {
    return this.http.get('http://localhost:8080/tests/', {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }
  // getTests(): Observable<ITest[]> {
  //   return this.http.get<ITest[]>(environment.apiUrl + `/test`).pipe(
  //     tap((data) => console.log('All: ', JSON.stringify(data))),
  //     catchError(this.handleError)
  //   );
  // }

  saveTest(test: ITest): Observable<ITest> {
    return this.http.post<ITest>(environment.apiUrl + '/tests/add', test).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
