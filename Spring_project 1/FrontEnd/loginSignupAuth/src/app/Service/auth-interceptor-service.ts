import {
  HttpEvent,
  HttpEventType,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpInterceptorFn,
  HttpRequest,
} from '@angular/common/http';
import { Observable, tap, throwError } from 'rxjs';
import { AuthDataService } from './authDataService';
import { CookieService } from 'ngx-cookie-service';
import { Injectable, inject } from '@angular/core';

//angular 15 > interceptor procedure
// check the app.component.ts for the root injection of this authInterceptor
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
    let xsrf = null;
    authService.XSRF_TOKEN.subscribe((CSRF_TOKEN) => (xsrf = CSRF_TOKEN));

    console.log('only User Details available');
    //console.log(authorization);
    if (xsrf) {
      console.log('XSRF available');
      console.log(xsrf);
      // httpHeaders = httpHeaders need to do this because .append returns a new HttpHeaders() like a builder pattern
      httpHeaders = httpHeaders
        // make sure you return the token as a X-XSRF-TOKEN header
        .append('X-XSRF-TOKEN', xsrf);
    } else {
      console.log('XSRF token error');
    }

    //trace the url movement
    console.log('URL is');
    console.log(req.url);

    console.log(
      '----------end XSRF-TOKEN and Authorization User available in Interceptor-----------'
    );

    //attach the XSRF if available
    const reqClone = req.clone({
      headers: httpHeaders,
    });
    return next(reqClone);
  }

  //initial login take
  return next(req)
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
