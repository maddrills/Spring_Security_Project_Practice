import { Component } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SuccessComponent } from '../success/success.component';
import { AdminComponent } from '../admin/admin.component';

@Component({
  selector: 'app-login-signup',
  standalone: true,
  imports: [CommonModule, RouterModule, SuccessComponent, AdminComponent],
  templateUrl: './login-signup.component.html',
  styleUrl: './login-signup.component.css',
})
export class LoginSignupComponent {}
