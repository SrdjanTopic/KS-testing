import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ConceptService {
  constructor(private http: HttpClient) {}

  getConcepts() {
    return this.http.get(`${environment.apiUrl}/concepts/`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }
  addConcepts(concepts: { concept: string }[]) {
    return this.http
      .post<any>(`${environment.apiUrl}/concepts/add`, concepts)
      .pipe(
        map((data) => console.log('Added concepts: ', JSON.stringify(data)))
      );
  }
  deleteConcepts(concepts: { id: number; concept: string }[]) {
    return this.http
      .post<any>(`${environment.apiUrl}/concepts/delete`, concepts)
      .pipe(
        map((data) => console.log('Deleted concepts: ', JSON.stringify(data)))
      );
  }
}