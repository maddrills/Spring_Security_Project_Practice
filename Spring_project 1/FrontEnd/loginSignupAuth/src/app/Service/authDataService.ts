import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthDataService {
  constructor(private http: HttpClient) {}

  testService() {
    console.log(this);
  }

  //posting basic auth to the backend

  postBasicAuthData(username: string, password: string) {
    console.log(username, password);
    //Header : Authorization
    //Value : Basic base64('YourOrgName:YourAPIKEY');
    window.sessionStorage.setItem(
      'userdetails',
      JSON.stringify({ username: username, password: password })
    );

    return this.http.get('http://localhost:8080/login/LoginUser', {
      observe: 'response',
      withCredentials: true,
    });
  }
}
