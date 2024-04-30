import { Routes } from '@angular/router';
import { LoginSignupComponent } from './login-signup/login-signup.component';
import { SuccessComponent } from './success/success.component';

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
      import('./login-signup/login-logout.routes').then(
        (mode) => mode.LOGIN_LOGOUT_ROUTS
      ),
  },
  {
    path: 'welcome',
    component: SuccessComponent,
  },
];
