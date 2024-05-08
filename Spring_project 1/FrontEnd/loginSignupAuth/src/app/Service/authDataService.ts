import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../Model/userModel';
import { BehaviorSubject, Subject, switchMap, tap } from 'rxjs';
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
    //get XSRF token
    console.log('-----------PRE USER DETAILS--------------');
    this.getAnXSRfToken()
      .pipe(
        switchMap(() => {
          console.log('in switch map');
          let takiTiki = null;
          this.XSRF_TOKEN.subscribe((token) => {
            console.log(token);
            takiTiki = token;
          });
          console.log(`Token is === ${takiTiki}`);
          //const httpHead = new HttpHeaders().append('X-XSRF-TOKEN', takiTiki!);
          return this.http
            .post<User[]>(
              'http://localhost:8080/user/get-all-users-post',
              null,
              {
                //headers: httpHead,
                observe: 'response',
                withCredentials: true,
              }
            )
            .pipe(
              tap((array) => {
                console.log('-----------PRE USER DETAILS--------------');
                console.log('Data chained');
                this.allUsersData.next(array.body);
              })
            );
        })
      )
      .subscribe({
        next: (a) => console.log('a', a),
        error: (e) => console.log('error in Map chain', e),
      });
    //
    // this.XSRF_TOKEN.subscribe((val) => console.log(val));
    // console.log('-----token gen-----');
    // return this.http
    //   .post<User[]>('http://localhost:8080/user/get-all-users-post', null, {
    //     observe: 'response',
    //     withCredentials: true,
    //   })
    //   .subscribe({
    //     next: (array) => {
    //       console.log(array.body);
    //       this.allUsersData.next(array.body);
    //     },
    //     error: (e) => {
    //       console.log(e);
    //     },
    //   });
  }

  //delete a user by id
  deleteAUser(id: number) {
    //get XSRF token
    return this.getAnXSRfToken().pipe(
      //Switch man look up
      switchMap(() => {
        //you have to chain for this to work because in angular HttpParams is immutable
        const idParameter = new HttpParams().set('userId', id);

        return this.http.delete<any>('http://localhost:8080/user/remove-user', {
          params: idParameter,
          observe: 'response',
          withCredentials: true,
        });
      })
    );
    //you have to chain for this to work because in angular HttpParams is immutable
    // const idParameter = new HttpParams().set('userId', id);
    // console.log('the id to be deleted ', id);
    // this.XSRF_TOKEN.subscribe((a) => {
    //   console.log('xxxxxxxxxxxx-----xxxxxxxxx', a);
    // });
    // this.tokenHistory.forEach((a) => console.log(a));
    // return this.http.delete<any>('http://localhost:8080/user/remove-user', {
    //   params: idParameter,
    //   observe: 'response',
    //   withCredentials: true,
    // });
  }

  //pre flight for XSRF token
  getAnXSRfToken() {
    //its a route to the back end that is not protected by any XSRF protection
    //must be generated per request
    //in backend check the route and you'll find what it returns is an XSRF token
    console.log('In XSRF token gen method');
    return this.http
      .post<{
        parameterName: string;
        headerName: string;
        token: string;
      }>('http://localhost:8080/user/getXSRfToken', null, {
        observe: 'response',
        withCredentials: true,
      })
      .pipe(
        tap((tokenData) => {
          console.log('REQ --- token ---generated ');
          console.log(tokenData.body?.token!);
          this.XSRF_TOKEN.next(tokenData.body?.token!);
        })
      );
    // .subscribe({
    //   next: (tokenData) => {
    //     console.log('REQ --- token ---generated ');
    //     console.log(tokenData.token);
    //     this.XSRF_TOKEN.next(tokenData.token);
    //   },
    //   error: (e) => console.log(e),
    // });
  }

  //logout of everything
  logOut() {
    console.log('Log out initialed');
    this.getAnXSRfToken()
      .pipe(
        switchMap(() => {
          console.log('in switch map');
          let takiTiki = null;
          this.XSRF_TOKEN.subscribe((token) => {
            console.log('Last Logout XSRF');
            console.log(token);
            takiTiki = token;
          });
          console.log(`Token is === ${takiTiki}`);
          return this.http.post<any>('http://localhost:8080/logout', null, {
            observe: 'response',
            withCredentials: true,
          });
        })
      )
      .subscribe({
        next: (n) => console.log(n),
        error: (er) => console.log(er),
      });
    // this.http
    //   .get('http://localhost:8080/logout', {
    //     responseType: 'text',
    //     observe: 'response',
    //     withCredentials: true,
    //   })
    //   .subscribe({
    //     next: (n) => console.log(n),
    //     error: (er) => console.log(er),
    //   });
    // window.sessionStorage.setItem('Authorization', '');
    // window.sessionStorage.setItem('userDetails', '');
    // this.authSubStatus.next(false);
    // this.isAdmin.next(false);
    // this.authenticated = false;
  }
}
