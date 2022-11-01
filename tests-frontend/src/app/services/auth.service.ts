import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(user: { username: string; password: string }) {
    return this.http.post('http://localhost:8080/auth/login', user, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }

  logout() {
    localStorage.clear();
    window.location.replace('');
  }
}
