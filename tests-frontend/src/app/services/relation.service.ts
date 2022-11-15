import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class RelationService {
  constructor(private http: HttpClient) {}

  getRelations() {
    return this.http.get('http://localhost:8080/relations/', {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }
}
