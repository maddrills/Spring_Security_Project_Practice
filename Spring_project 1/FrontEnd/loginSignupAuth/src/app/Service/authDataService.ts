import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../Model/userModel';
import { BehaviorSubject, Subject, tap } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({ providedIn: 'root' })
export class AuthDataService {
  authenticated = false;

  //its is like a football score board .. even if you join (Subscribe) late you will still get the latest value
  // in our case there will be an initial value of false
  authSubStatus = new BehaviorSubject<boolean>(false);
  isAdmin = new BehaviorSubject<boolean>(false);
  allUsersData = new BehaviorSubject<User[] | null>(null);
  XSRF_TOKEN = new BehaviorSubject<String | null>(null);

  tokenHistory: Array<String> = [];

  constructor(private http: HttpClient, private cookey: CookieService) {}

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

  //get all userData
  //remember subscription for this is made in the welcome component if the user is an admin
  // getAllUserData() {
  //   return this.http
  //     .get<User[]>('http://localhost:8080/user/get-all-users')
  //     .subscribe({
  //       next: (array) => {
  //         this.allUsersData.next(array);
  //       },
  //       error: (e) => console.log(e),
  //     });
  // }
  getAllUserData() {
    return this.http
      .post<any>('http://localhost:8080/user/get-all-users-post', null)
      .subscribe({
        next: (array) => {
          this.allUsersData.next(array);
        },
        error: (e) => console.log(e),
      });
  }

  //delete a user by id
  deleteAUser(id: number) {
    //you have to chain for this to work because in angular HttpParams is immutable
    const idParameter = new HttpParams().set('userId', id);
    console.log('the id to be deleted ', id);
    this.XSRF_TOKEN.subscribe((a) => {
      console.log('xxxxxxxxxxxx-----xxxxxxxxx', a);
    });
    this.tokenHistory.forEach((a) => console.log(a));
    return this.http.delete<any>('http://localhost:8080/user/remove-user', {
      params: idParameter,
    });
  }

  //logout of everything
  logOut() {
    window.sessionStorage.setItem('Authorization', '');
    window.sessionStorage.setItem('userDetails', '');
    this.authSubStatus.next(false);
    this.isAdmin.next(false);
    this.authenticated = false;
  }
}
