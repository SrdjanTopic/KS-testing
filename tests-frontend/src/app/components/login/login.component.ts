import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {}

  login() {
    if (this.username !== '' && this.password !== '')
      this.authService
        .login({ username: this.username, password: this.password })
        .subscribe((data: any) => {
          window.location.replace('');
        });
    else alert('Plese input username and password!');
  }
}
