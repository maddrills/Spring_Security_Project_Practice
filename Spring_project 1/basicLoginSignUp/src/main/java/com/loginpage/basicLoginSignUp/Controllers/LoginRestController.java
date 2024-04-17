package com.loginpage.basicLoginSignUp.Controllers;

import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;



@RestController
@RequestMapping("/login")
public class LoginRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/LoginUser")
    public void testThis(){
        System.out.println("Login");

        String username = "mat@matify.com";
        String password = "12345";

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );

        }catch (AuthenticationException e){
            System.out.println("404 in service");
            System.out.println(e);
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Person person = new Person("Kelly",26, "mat@matify.com","hsbc");
    }


}
