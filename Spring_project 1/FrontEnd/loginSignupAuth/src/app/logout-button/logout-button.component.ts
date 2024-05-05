import { Component } from '@angular/core';
import { AuthDataService } from '../Service/authDataService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout-button',
  standalone: true,
  imports: [],
  templateUrl: './logout-button.component.html',
  styleUrl: './logout-button.component.css',
})
export class LogoutButtonComponent {
  constructor(
    private authDataService: AuthDataService,
    private router: Router
  ) {}

  logOutClicked() {
    console.log('Logout Clicked');
    this.authDataService.authSubStatus.next(false);
    this.authDataService.isAdmin.next(false);
    this.authDataService.allUsersData.next(null);
    this.authDataService.logOut();
    this.router.navigate(['']);
  }
}
