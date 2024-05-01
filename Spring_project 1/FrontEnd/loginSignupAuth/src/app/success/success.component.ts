import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthDataService } from '../Service/authDataService';
import { Subscription, map } from 'rxjs';
import { User } from '../Model/userModel';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-success',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './success.component.html',
  styleUrl: './success.component.css',
})
export class SuccessComponent implements OnInit, OnDestroy {
  authenticated = false;
  userDetails: User | null = null;
  //manage subscription

  constructor(private authService: AuthDataService) {}

  ngOnInit(): void {
    this.authenticated = this.authService.authenticated;
    this.userDetails = JSON.parse(
      window.sessionStorage.getItem('userDetails')!
    );
  }

  check() {
    //false here to
    console.log(this.authenticated);
    console.log(window.sessionStorage.getItem('userDetails'));
    console.log(JSON.parse(window.sessionStorage.getItem('userDetails')!));

    console.log(this.userDetails?.authorities);
  }

  ngOnDestroy(): void {
    console.log('Method destroyed');
  }
}
