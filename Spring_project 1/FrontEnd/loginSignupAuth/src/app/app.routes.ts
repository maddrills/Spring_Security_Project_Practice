import { Routes } from '@angular/router';
import { LoginSignupComponent } from './login-signup/login-signup.component';
import { SuccessComponent } from './success/success.component';
import { AuthGuard } from './Service/authGuardService';
import { AdminComponent } from './admin/admin.component';
import { AuthGuardAdminService } from './Service/authGuardAdminService';

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
    canActivate: [AuthGuard.authGuardFn],
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuardAdminService.authGuardFn],
  },
];
