import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map, tap } from 'rxjs/operators';

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

  generateRealKS(results: any) {
    return this.http
      .post<any>(`http://localhost:5000/iita`, results)
      .pipe(tap());
  }

  try() {
    return this.http.get(`http://localhost:5000/`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }
}
