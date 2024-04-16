import { Component, OnInit } from "@angular/core";
import { User } from "src/app/model/user.model";
import { NgForm } from "@angular/forms";
import { LoginService } from "src/app/services/login/login.service";
import { Router } from "@angular/router";
import { getCookie } from "typescript-cookie";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
  authStatus: string = "";
  model = new User();

  constructor(private loginService: LoginService, private router: Router) {}

  ngOnInit(): void {}
  // login form model
  validateUser(loginForm: NgForm) {
    this.loginService
      //send the login details to the backend and get the response
      .validateLoginDetails(this.model)
      //the response is an Http response
      .subscribe((responseData) => {
        this.model = <any>responseData.body;

        //bellow does saves the CSRF token related stuff
        let xsrf = getCookie("XSRF-TOKEN")!;
        window.sessionStorage.setItem("XSRF-TOKEN", xsrf);

        this.model.authStatus = "AUTH";
        window.sessionStorage.setItem(
          "userdetails",
          JSON.stringify(this.model)
        );
        this.router.navigate(["dashboard"]);
      });
  }
}
