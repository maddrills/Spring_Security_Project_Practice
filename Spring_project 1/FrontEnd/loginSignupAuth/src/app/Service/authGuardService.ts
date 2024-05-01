import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import {
  ActivatedRouteSnapshot,
  CanActivateChildFn,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { AuthDataService } from './authDataService';

@Injectable({ providedIn: 'root' })
export class AuthGuard {
  static authGuardFn: CanActivateFn = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> => {
    const router = inject(Router);
    const authService = inject(AuthDataService);

    return authService.authSubStatus.pipe(
      map((status) => {
        if (status) return true;
        return router.createUrlTree(['/user']);
      })
    );
  };
}
