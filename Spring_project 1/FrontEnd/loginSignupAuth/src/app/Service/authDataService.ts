import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../Model/userModel';
import { BehaviorSubject, Subject, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthDataService {
  authenticated = false;

  //its is like a football score board .. even if you join (Subscribe) late you will still get the latest value
  // in our case there will be an initial value of false
  authSubStatus = new BehaviorSubject<boolean>(false);
  isAdmin = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {}

  testService() {
    console.log(this);
  }

  //LOG-IN posting basic auth to the backend

  postBasicAuthData(username: string, password: string) {
    console.log(username, password);

    const header = new HttpHeaders();

    const authHeader = header.set(
      'Authorization',
      'Basic ' + window.btoa(username + ':' + password)
    );

    return this.http
      .get<any>('http://localhost:8080/login/LoginUser', {
        headers: authHeader,
        observe: 'response',
        withCredentials: true,
      })
      .pipe(
        tap((responseData) => {
          if (responseData) {
            this.authenticated = true;
            this.authSubStatus.next(this.authenticated);
            //admin check
            if (this.authorityCheck(responseData.body.authorities)) {
              //if admin exists
              this.isAdmin.next(true);
            }
          }
        })
      );
  }

  //check if a list of privileges contains ROLE_Admin
  private authorityCheck(roles: { authority: string }[]): boolean {
    let status = false;

    roles.forEach((auth) => {
      console.log(auth.authority);
      if (auth.authority == 'ROLE_Admin') {
        status = true;
      }
    });
    return status;
  }

  //SIGN-UP sign up a new user

  newUserDetailsPost(
    username: string,
    password: string,
    age: number,
    email: string
  ) {
    console.log(username, password, age, email);

    return this.http.post<any>('http://localhost:8080/Sign-up/signup-user', {
      name: username,
      password: password,
      age: age,
      email: email,
    });
  }
}
