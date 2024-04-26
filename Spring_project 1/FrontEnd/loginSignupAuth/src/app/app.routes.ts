import { Routes } from '@angular/router';
import { LoginSignupComponent } from './login-signup/login-signup.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'user',
    pathMatch: 'full',
  },
  {
    path: 'user',
    component: LoginSignupComponent,
    //login signup routs in lazy mode
    loadChildren: () =>
      import('./login-signup/login-logout.routs').then(
        (mode) => mode.LOGIN_LOGOUT_ROUTS
      ),
  },
];
