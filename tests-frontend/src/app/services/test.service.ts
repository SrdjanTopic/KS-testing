import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
}
