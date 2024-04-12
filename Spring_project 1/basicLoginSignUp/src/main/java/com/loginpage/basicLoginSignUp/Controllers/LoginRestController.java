package com.loginpage.basicLoginSignUp.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginRestController {

    @GetMapping("/LoginUser")
    public void testThis(){
        System.out.println("Login");
    }


}
