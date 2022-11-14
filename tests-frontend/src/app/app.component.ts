import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(private authService: AuthService) {}

  title = 'tests-frontend';
  JWTHelper: JwtHelperService = new JwtHelperService();
  token: string | null = localStorage.getItem('jwt');
  currentUser: any = this.JWTHelper.decodeToken(
    this.token ? this.token : undefined
  );

  logout() {
    this.authService.logout();
  }

  checkStudent(): boolean {
    return this.currentUser.roles.some(
      (role: any) => role.authority === 'ROLE_STUDENT'
    );
  }

  checkTeacher(): boolean {
    return this.currentUser.roles.some(
      (role: any) => role.authority === 'ROLE_TEACHER'
    );
  }
}
