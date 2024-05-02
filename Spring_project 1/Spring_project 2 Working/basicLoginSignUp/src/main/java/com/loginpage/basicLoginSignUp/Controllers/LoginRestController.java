package com.loginpage.basicLoginSignUp.Controllers;

import com.loginpage.basicLoginSignUp.Services.PersonDetailsService;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginRestController {

    @Autowired
    PersonDetailsService personDetailsService;

    @GetMapping("/LoginUser")
    public Person testThis(Authentication authentication){
        System.out.println("Login");

        String username = authentication.getName();

        return personDetailsService.getAllDetailsOfAPerson(username);
    }


}
