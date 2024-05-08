import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthDataService } from '../../Service/authDataService';
import { WrongInputDirective } from '../../Directive/wrong-input.directive';
import { CommonModule } from '@angular/common';
import { User } from '../../Model/userModel';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, FormsModule, WrongInputDirective, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../login-signup.component.css'],
  // styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  formCondition = false;
  constructor(private authService: AuthDataService, private router: Router) {}

  ngOnInit(): void {
    if (window.sessionStorage.getItem('userDetails'))
      this.router.navigate(['welcome']);
  }

  onSubmit(formSettings: NgForm) {
    const formDataFields = formSettings.form.value;
    const username = formDataFields.username;
    const password = formDataFields.password;

    //   .subscribe({
    //     complete: () => { ... }, // completeHandler
    //     error: () => { ... },    // errorHandler
    //     next: () => { ... },     // nextHandler
    //     someOtherProperty: 42
    // });
    this.authService.postBasicAuthData(username, password).subscribe({
      next: (v) => {
        console.log(v);
        const user = <User>v.body;
        //if you want it to last if the user closes the app use local storage
        //session storage will only maintain a page refresh state
        window.sessionStorage.setItem('userDetails', JSON.stringify(user!));
        console.log('Success');
        this.router.navigate(['welcome']);
      },
      error: (e) => {
        console.log(e);
        console.log('Error');
        this.formCondition = true;
      },
      complete: () => console.info('complete'),
    });
    // formSettings.reset();
  }

  resetInputStatus() {
    this.formCondition = false;
  }
}
