package com.loginpage.basicLoginSignUp.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Sign-up")
public class SignUpRestController {
    @PostMapping("signup-user")
    public void testThis(){
        System.out.println("Sign up user");
    }
}
