import { IUser } from './../../model/user';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  loggedInUser!: IUser;
  errorMessage!: string;

  constructor(private authService: AuthService,
    private userService: UserService) { }

  currentUser: string | null = localStorage.getItem('jwt');
  
  logout() {
    this.authService.logout();
  }

  ngOnInit(): void {
    this.getUser();
  }

  getUser() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.loggedInUser = user;
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }

}
