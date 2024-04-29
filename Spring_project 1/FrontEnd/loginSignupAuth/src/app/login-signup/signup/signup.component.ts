import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { AuthDataService } from '../../Service/authDataService';
import { WrongInputDirective } from '../../Directive/wrong-input.directive';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [RouterLink, FormsModule, WrongInputDirective, CommonModule],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css', '../login-signup.component.css'],
})
export class SignupComponent {
  formCondition = false;
  constructor(
    private authService: AuthDataService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  onSubmitSignUp(fullForm: NgForm) {
    console.log(fullForm.form);

    const formData = fullForm.form.value;

    const username = formData.username;
    const password = formData.password;
    const age = formData.age;
    const email = formData.email;

    this.authService
      .newUserDetailsPost(username, password, age, email)
      .subscribe({
        next: (v) => {
          console.log(v);
          console.log('Success');
          this.router.navigate(['../log-in'], {
            relativeTo: this.activatedRoute,
          });
        },
        error: (e) => {
          console.log(e);
          console.log('Error');
          this.formCondition = true;
          //change the dom to handle error
        },
        complete: () => console.info('complete'),
      });
  }

  resetInputStatus() {
    this.formCondition = false;
  }
}
