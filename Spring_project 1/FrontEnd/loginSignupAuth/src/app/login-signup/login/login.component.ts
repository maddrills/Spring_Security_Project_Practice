import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthDataService } from '../../Service/authDataService';
import { WrongInputDirective } from '../../Directive/wrong-input.directive';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, FormsModule, WrongInputDirective, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../login-signup.component.css'],
  // styleUrl: './login.component.css'
})
export class LoginComponent {
  formCondition = false;
  constructor(private authService: AuthDataService, private router: Router) {}

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
        console.log('Success');
      },
      error: (e) => {
        console.log(e);
        console.log('Error');
        this.formCondition = true;
        console.log(this.formCondition);
      },
      complete: () => console.info('complete'),
    });
    // formSettings.reset();
  }

  resetInputStatus() {
    this.formCondition = false;
  }
}
