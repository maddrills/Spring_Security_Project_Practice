import { Injectable } from "@angular/core";
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpErrorResponse,
  HttpHeaders,
} from "@angular/common/http";
import { Router } from "@angular/router";
import { tap } from "rxjs/operators";
import { User } from "src/app/model/user.model";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {
  user = new User();
  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let httpHeaders = new HttpHeaders();
    //the bellow goes to the backend with user entered credentials
    if (sessionStorage.getItem("userdetails")) {
      //this was set in login service
      this.user = JSON.parse(sessionStorage.getItem("userdetails")!);
    }
    //this is automatically proceesed by springs basicAuthenticationMode you spring under non JWT mode
    if (this.user && this.user.password && this.user.email) {
      httpHeaders = httpHeaders.append(
        "Authorization",
        "Basic " + window.btoa(this.user.email + ":" + this.user.password)
      );
    }

    //send the tooken back per request
    let xsrf = sessionStorage.getItem("XSRF-TOKEN");
    if (xsrf) {
      //spring security accepts csrf of this header X-XSRF-TOKEN
      httpHeaders = httpHeaders.append("X-XSRF-TOKEN", xsrf);
    }

    httpHeaders = httpHeaders.append("X-Requested-With", "XMLHttpRequest");
    const xhr = req.clone({
      headers: httpHeaders,
    });
    return next.handle(xhr).pipe(
      tap((err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== 401) {
            return;
          }
          this.router.navigate(["dashboard"]);
        }
      })
    );
  }
}
