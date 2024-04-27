import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthDataService } from '../../Service/authDataService';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../login-signup.component.css'],
  // styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private authService: AuthDataService) {}

  onSubmit(formSettings: NgForm) {
    const formDataFields = formSettings.form.value;

    const username = formDataFields.username;
    const password = formDataFields.password;

    this.authService.postBasicAuthData(username, password).subscribe(
      (resp: any) => {
        console.log(resp);
      },
      (error: any) => console.log(error)
    );

    // formSettings.reset();
  }
}
