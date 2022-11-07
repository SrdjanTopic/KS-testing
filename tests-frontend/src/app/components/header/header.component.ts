import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService,
    private userService: UserService) { }

  currentUser: string | null = localStorage.getItem('jwt');
  
  logout() {
    this.authService.logout();
  }

  ngOnInit(): void {
  }

}
