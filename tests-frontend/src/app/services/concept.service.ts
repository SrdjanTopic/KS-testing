import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ConceptService {
  constructor(private http: HttpClient) {}

  getConcepts() {
    return this.http.get('http://localhost:8080/concepts/', {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }
}
