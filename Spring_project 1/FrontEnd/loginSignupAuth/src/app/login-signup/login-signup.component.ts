import { Component } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login-signup',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './login-signup.component.html',
  styleUrl: './login-signup.component.css',
})
export class LoginSignupComponent {}
