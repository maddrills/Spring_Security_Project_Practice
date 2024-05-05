import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthDataService } from '../Service/authDataService';
import { Subscription, map } from 'rxjs';
import { User } from '../Model/userModel';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { LogoutButtonComponent } from '../logout-button/logout-button.component';

@Component({
  selector: 'app-success',
  standalone: true,
  imports: [CommonModule, FormsModule, LogoutButtonComponent],
  templateUrl: './success.component.html',
  styleUrl: './success.component.css',
})
export class SuccessComponent implements OnInit, OnDestroy {
  authenticated = false;
  adminHere = false;
  userDetails: User | null = null;
  //manage subscription

  constructor(
    private authService: AuthDataService,
    private router: Router,
    private activeRoute: ActivatedRoute //use when you want welcome/**
  ) {}

  ngOnInit(): void {
    this.authenticated = this.authService.authenticated;
    this.userDetails = JSON.parse(
      //! at the end means your confident you wont get null back
      window.sessionStorage.getItem('userDetails')!
    );
    console.log('----ADMIN---OUT---', this.adminHere);
    this.authService.isAdmin.subscribe((adminStatus) => {
      console.log('----ADMIN---IN---', this.adminHere);
      console.log('----ADMIN---Lambda', adminStatus);
      if (adminStatus) {
        this.adminHere = true;
        this.authService.getAllUserData();
      }
    });
    console.log(this.adminHere);
  }

  adminSwitch() {
    //false here to
    // console.log(this.authenticated);
    // console.log(window.sessionStorage.getItem('userDetails'));
    // console.log(JSON.parse(window.sessionStorage.getItem('userDetails')!));

    // console.log(this.userDetails?.authorities);
    this.router.navigate(['admin']);
  }

  ngOnDestroy(): void {
    console.log('Method destroyed');
  }
}
