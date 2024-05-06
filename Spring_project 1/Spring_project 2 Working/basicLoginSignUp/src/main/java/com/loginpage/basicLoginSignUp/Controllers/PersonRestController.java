package com.loginpage.basicLoginSignUp.Controllers;

import com.loginpage.basicLoginSignUp.Services.PersonDetailsService;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class PersonRestController {

    @Autowired
    PersonDetailsService personDetailsService;

    @PostMapping("/getXSRfToken")
    public CsrfToken getAllUserData(CsrfToken csrfToken){
        return csrfToken;
    }

    @GetMapping("/userOnly")
    public String userOnly(){
        return "User Only Login";
    }

    //if you Dont implement CSRF this will not work
    //by default spring blocks all but GET methods
    @PostMapping("/addUser")
    public String addAUser(){
        return "User Added";
    }


    @GetMapping("/get-all-users")
    public Collection<Person> allUsersInDb(){
        return this.personDetailsService.getAllUsersRole_user();
    }

    @PostMapping("/get-all-users-post")
    public Collection<Person> getAllUsersInDb(){
        System.out.print("All users call");
        return this.personDetailsService.getAllUsersRole_user();
    }


    //remove user by username
    @DeleteMapping("/remove-user")
    public ResponseEntity<?> removeAUser(@RequestParam int userId){

        if(userId <= 0) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);

        System.out.print(userId);

        this.personDetailsService.deletePerson(userId);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
