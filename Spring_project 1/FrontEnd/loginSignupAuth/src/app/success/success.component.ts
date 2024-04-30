import { Component, OnInit } from '@angular/core';
import { AuthDataService } from '../Service/authDataService';
import { map } from 'rxjs';
import { User } from '../Model/userModel';

@Component({
  selector: 'app-success',
  standalone: true,
  imports: [],
  templateUrl: './success.component.html',
  styleUrl: './success.component.css',
})
export class SuccessComponent implements OnInit {
  authenticated = false;

  constructor(private authService: AuthDataService) {}

  ngOnInit(): void {
    this.authService.userSubject.subscribe((user) => {
      this.authenticated = true;
      console.log('In the body of subscribe');
      console.log(this.authenticated);
    });
    //still false
    console.log(this.authenticated);
  }

  check() {
    //false here to
    console.log(this.authenticated);
  }
}
