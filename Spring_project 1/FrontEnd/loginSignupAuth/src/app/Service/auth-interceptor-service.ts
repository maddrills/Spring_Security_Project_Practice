import {
  HttpEvent,
  HttpEventType,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpInterceptorFn,
  HttpRequest,
} from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthDataService } from './authDataService';
import { CookieService } from 'ngx-cookie-service';
import { Injectable, inject } from '@angular/core';

//angular 15 > interceptor procedure
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  console.log('Interceptor Fired');

  //checks
  let loginStatus = false;

  //header modes
  let httpHeaders = new HttpHeaders();

  //injections
  const authService = inject(AuthDataService);
  const cookieService = inject(CookieService);

  //check if user logged in
  authService.authSubStatus.subscribe((status) => (loginStatus = status));
  if (loginStatus) {
    console.log('----------User available in Interceptor-----------');
    //then attach Authorization and XSRF-TOKEN headers
    const authorization = sessionStorage.getItem('Authorization');
    let xsrf = null;
    authService.XSRF_TOKEN.subscribe((CSRF_TOKEN) => (xsrf = CSRF_TOKEN));

    //do not break till xsrf token is generated or there is a legit error
    if (authorization && xsrf) {
      console.log('Aauth Details available');
      console.log(xsrf);
      httpHeaders = httpHeaders
        .append('Authorization', authorization)
        // make sure you return the token as a X-XSRF-TOKEN header
        .append('X-XSRF-TOKEN', xsrf);
    }

    console.log(
      '----------end XSRF-TOKEN and Authorization User available in Interceptor-----------'
    );
  }
  if (loginStatus) {
    const reqClone = req.clone({
      headers: httpHeaders,
    });
    return next(reqClone).pipe(
      tap((resp) => {
        if (resp.type === HttpEventType.Response) {
          console.log('After burner login call');
          //make sure CORS is handled for this
          authService.tokenHistory.push(resp.headers.get('X-XSRF-TOKEN')!);
          authService.XSRF_TOKEN.next(resp.headers.get('X-XSRF-TOKEN')!);
        }
      })
    );
  }

  return next(req).pipe(
    tap((event) => {
      if (event.type === HttpEventType.Response) {
        console.log('After burner');
        //make sure CORS is handled for this
        authService.tokenHistory.push(event.headers.get('X-XSRF-TOKEN')!);
        authService.XSRF_TOKEN.next(event.headers.get('X-XSRF-TOKEN')!);
        console.log(event.headers.get('X-XSRF-TOKEN')!);
        console.log(event.headers.get('Authorization')!);
        console.log('After burner end');
      }
    })
  );
};

// below is the old way for angular 14 <
// export class AuthInterceptorService implements HttpInterceptor {
//   loginStatus = false;

//   constructor(
//     private authService: AuthDataService,
//     private cookieService: CookieService
//   ) {}

//   intercept(
//     req: HttpRequest<any>,
//     next: HttpHandler
//   ): Observable<HttpEvent<any>> {
//     this.authService.authSubStatus.subscribe((status) => {
//       this.loginStatus = status;
//     });

//     console.log('Interceptor Fired');
//     //if user logged in
//     if (this.loginStatus) {
//       let httpHeaders = new HttpHeaders();

//       console.log('Interceptor user logged in');

//       const authorization = sessionStorage.getItem('Authorization');
//       //if jwt token exists in storage
//       if (authorization) {
//         httpHeaders = httpHeaders.append('Authorization', authorization);
//       }
//       //if xsrf token exists
//       const xxrf = this.cookieService.get('XSRF-TOKEN');
//       if (xxrf) {
//         httpHeaders = httpHeaders.append('Authorization', xxrf);
//       }

//       const xhr = req.clone({
//         headers: httpHeaders,
//       });

//       const authReq = req.clone({
//         headers: req.headers.set('Authorization', 'authToken'),
//       });
//       return next.handle(xhr);
//     }

//     return next.handle(req);
//   }
// }
