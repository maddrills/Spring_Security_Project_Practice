import { Injectable, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthDataService } from './authDataService';

//check app.router.ts for implimentaion
@Injectable({ providedIn: 'root' })
export class AuthGuardAdminService {
  static authGuardFn: CanActivateFn = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> => {
    const router = inject(Router);
    const authService = inject(AuthDataService);

    return authService.isAdmin.pipe(
      map((status) => {
        if (status) return true;
        return router.createUrlTree(['/welcome']);
      })
    );
  };
}
