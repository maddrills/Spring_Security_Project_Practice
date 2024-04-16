import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { User } from "src/app/model/user.model";
import { Observable, Subject } from "rxjs";
import { AppConstants } from "src/app/constants/app.constants";
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: "root",
})
export class LoginService {
  constructor(private http: HttpClient) {}

  //sending the information to the backend
  validateLoginDetails(user: User) {
    //sending the credentials in the interceptor
    window.sessionStorage.setItem("userdetails", JSON.stringify(user));
    return this.http.get(environment.rooturl + AppConstants.LOGIN_API_URL, {
      observe: "response",
      withCredentials: true,
    });
  }
}
