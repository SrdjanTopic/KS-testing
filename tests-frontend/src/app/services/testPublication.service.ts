import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { catchError, tap } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TestPublicationService {
  constructor(private http: HttpClient) {}

  getTestPublications() {
    return this.http.get(`${environment.apiUrl}/relations/`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  rePublishOldVersionOfTest(publicationId: number) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/testPublications/${publicationId}/republish`,
        null
      )
      .pipe(
        tap((res) => console.log('All: ', JSON.stringify(res))),
        catchError(this.handleError)
      );
  }

  publishNewVersionOfTest(data: any) {
    return this.http
      .post<any>(
        `${environment.apiUrl}/testPublications/publishNewVersion`,
        data
      )
      .pipe(
        tap((res) => console.log('All: ', JSON.stringify(res))),
        catchError(this.handleError)
      );
  }
  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
