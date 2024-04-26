import { Routes } from '@angular/router';
import { LoginSignupComponent } from './login-signup/login-signup.component';

export const routes: Routes = [
  {
    path: '',
    component: LoginSignupComponent,
    pathMatch: 'full',
  },
  {
    path: 'user',
    component: LoginSignupComponent,
  },
];
