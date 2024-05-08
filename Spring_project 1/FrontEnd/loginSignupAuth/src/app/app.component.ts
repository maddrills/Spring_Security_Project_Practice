import { Component, OnInit } from '@angular/core';
import { LoginSignupComponent } from './login-signup/login-signup.component';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AuthDataService } from './Service/authDataService';
import { User } from './Model/userModel';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [LoginSignupComponent, CommonModule, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  constructor(private router: Router, private authService: AuthDataService) {}

  ngOnInit(): void {
    //all done for autoLogin
    console.log('welcome comp');
    console.log(window.sessionStorage.getItem('userDetails'));

    const user: User | undefined | null = JSON.parse(
      window.sessionStorage.getItem('userDetails')!
    );

    if (user) {
      console.log('user available');
      this.authService.authSubStatus.next(true);
      //check if user is an admin
      if (this.authService.authorityCheck(user.authorities)) {
        this.authService.isAdmin.next(true);
      }
      this.router.navigate(['welcome']);
    }
  }
  title = 'loginSignupAuth';
}
