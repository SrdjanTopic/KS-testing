import { Injectable, Injector } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Role } from '../model/role.enum';
import { IRole, IToken } from '../model/user';
import { Observable } from 'rxjs';
import { UserService } from './user.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private router: Router,
    private http: HttpClient,
    private injector: Injector) {}

  private accessToken = localStorage.getItem('jwt');

  login(user: { username: string; password: string }) {
    return this.http.post<any>(`${environment.apiUrl}/auth/login`, user, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    }).pipe(
      map((res: IToken) => {
        localStorage.setItem('jwt', res.accessToken);
        this.accessToken = res.accessToken;
      })
    );
  }

  logout() {
    this.purgeUser();
    localStorage.removeItem('jwt');
    this.accessToken = null;
    this.router.navigate(['']);
    window.location.replace('');
  }

  purgeUser() {
    const userService = this.injector.get<UserService>(UserService);
    userService.purgeUser();
  }

  getToken() {
    return this.accessToken;
  }

  isLoggedIn() {
    return this.accessToken !== undefined && this.accessToken !== null;
  }

  isAuthorized(roles: Role[]): Observable<boolean> {
    var requiredRoles: IRole[] = [];
    roles.forEach((role) => requiredRoles.push({ id: 0, authority: role } as IRole));
    return this.http.post<any>(
      `${environment.apiUrl}/user/authorize`,
      requiredRoles
    );
  }

  isAdminFirstTimeLoggedIn(): Observable<boolean> {
    return this.http.get<any>(`${environment.apiUrl}/admin/firstTimeLoggedIn`);
  }
}
