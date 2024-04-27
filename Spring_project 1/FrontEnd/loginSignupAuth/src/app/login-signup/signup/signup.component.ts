import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { AuthDataService } from '../../Service/authDataService';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css', '../login-signup.component.css'],
})
export class SignupComponent {
  constructor(private authService: AuthDataService) {}

  onSubmitSignUp(fullForm: NgForm) {
    console.log(fullForm.form);

    const formData = fullForm.form.value;

    const username = formData.username;
    const password = formData.password;
    const age = formData.age;
    const email = formData.email;

    this.authService
      .newUserDetailsPost(username, password, age, email)
      .subscribe((returnVal) => console.log(returnVal));
  }
}
