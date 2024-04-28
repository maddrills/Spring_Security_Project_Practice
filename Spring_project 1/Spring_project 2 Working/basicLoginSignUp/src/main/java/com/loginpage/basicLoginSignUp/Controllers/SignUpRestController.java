package com.loginpage.basicLoginSignUp.Controllers;

import com.loginpage.basicLoginSignUp.DTO.AllUserData;
import com.loginpage.basicLoginSignUp.Services.PersonDetailsService;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/Sign-up")
public class SignUpRestController {

    @Autowired
    private PersonDetailsService personDetails;

    @PostMapping("/signup-user")
    public ResponseEntity<?> testThis(@RequestBody AllUserData allUserData){

        System.out.println(allUserData);

        boolean status = personDetails.addAPersonWithAnExistingRole(
                new Person(
                        allUserData.getName(),
                        allUserData.getAge(),
                        allUserData.getEmail(),
                        allUserData.getPassword()), new HashSet<String>(List.of("User"))
        );

        if(!status){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
