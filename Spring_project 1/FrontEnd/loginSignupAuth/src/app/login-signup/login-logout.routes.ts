import { Route } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';

export const LOGIN_LOGOUT_ROUTS: Route[] = [
  {
    path: '',
    redirectTo: 'log-in',
    pathMatch: 'full',
  },
  {
    path: 'log-in',
    component: LoginComponent,
  },
  {
    path: 'sign-up',
    component: SignupComponent,
  },
];
