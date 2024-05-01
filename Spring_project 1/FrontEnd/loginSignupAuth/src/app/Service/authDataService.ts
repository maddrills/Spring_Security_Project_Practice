import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../Model/userModel';
import { Subject, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthDataService {
  authenticated = false;

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
          if (responseData) this.authenticated = true;
        })
      );
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
