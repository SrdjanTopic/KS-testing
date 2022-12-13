import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RealKSService {
  constructor(private http: HttpClient) {}

  getRealRelations() {
    return this.http.get(`${environment.apiUrl}/realKS/`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  getRealKSForTest(testId: Number) {
    return this.http.get(`${environment.apiUrl}/realKS/${testId}`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  createRealKSForTest(results: any, testId: Number) {
    return this.http
      .post<any>(`${environment.apiUrl}/realKS/${testId}/create`, results)
      .pipe(tap());
  }
}
