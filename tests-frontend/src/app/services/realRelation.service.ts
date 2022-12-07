import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RealRelationService {
  constructor(private http: HttpClient) {}

  getRealRelations() {
    return this.http.get(`${environment.apiUrl}/realRelations/`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  saveRealRelations(results: any) {
    return this.http
      .post<any>(`${environment.apiUrl}/realRelations/create`, results)
      .pipe(tap());
  }
}
