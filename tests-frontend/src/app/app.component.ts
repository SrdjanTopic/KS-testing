import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(private authService: AuthService) {}

  title = 'tests-frontend';

  currentUser: string | null = localStorage.getItem('jwt');

  logout() {
    this.authService.logout();
  }
}
