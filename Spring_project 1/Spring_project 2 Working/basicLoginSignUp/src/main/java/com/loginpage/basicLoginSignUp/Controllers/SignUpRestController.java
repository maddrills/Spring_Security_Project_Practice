package com.loginpage.basicLoginSignUp.Controllers;

import com.loginpage.basicLoginSignUp.DTO.AllUserData;
import com.loginpage.basicLoginSignUp.Services.PersonDetailsService;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/Sign-up")
public class SignUpRestController {

    @Autowired
    private PersonDetailsService personDetails;

    @PostMapping("/signup-user")
    public void testThis(@RequestBody AllUserData allUserData){

        System.out.println(allUserData);

        personDetails.addAPersonWithAnExistingRole(
                new Person(
                        allUserData.getName(),
                        allUserData.getAge(),
                        allUserData.getEmail(),
                        allUserData.getPassword()), new HashSet<String>(List.of("User"))
        );
    }
}
