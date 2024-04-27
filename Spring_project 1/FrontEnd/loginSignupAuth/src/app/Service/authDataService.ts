import { HttpClient, HttpHeaders } from '@angular/common/http';
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

    const header = new HttpHeaders();
    // Update: adding multiple headers
    //let headers = new HttpHeaders();
    //headers = headers.set('h1', 'v1').set('h2','v2');
    //or
    //const headers = new HttpHeaders({'h1':'v1','h2':'v2'});
    //spring basic username pass auth format
    //The btoa() method creates a Base64-encoded ASCII string from a binary string (i.e., a string in which each character in the string is treated as a byte of binary data).
    const authHeader = header.set(
      'Authorization',
      'Basic ' + window.btoa(username + ':' + password)
    );

    return this.http.get<any>('http://localhost:8080/login/LoginUser', {
      headers: authHeader,
      observe: 'response',
      withCredentials: true,
    });
  }
}
