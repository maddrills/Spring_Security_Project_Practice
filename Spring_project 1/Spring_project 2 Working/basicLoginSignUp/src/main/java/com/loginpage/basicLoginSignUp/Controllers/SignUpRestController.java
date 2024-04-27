package com.loginpage.basicLoginSignUp.Controllers;

import com.loginpage.basicLoginSignUp.DTO.AllUserData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Sign-up")
public class SignUpRestController {
    @PostMapping("/signup-user")
    public void testThis(@RequestBody AllUserData allUserData){

        System.out.println(allUserData);
        System.out.println("Sign up user");
    }
}
