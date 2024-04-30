package com.loginpage.basicLoginSignUp.Controllers;

import com.loginpage.basicLoginSignUp.Services.PersonDetailsService;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class PersonRestController {

    @Autowired
    PersonDetailsService personDetailsService;

//    @GetMapping("/getAllUserData")
//    public AllUserData getAllUserData(){
//
//        Person person = personDetailsService.getPersonByName("Mathew Francis");
//
//        AllUserData allUserData = new AllUserData(
//                person.getAuthorities(),
//                person.getEmail(),
//                person.getAge(),
//                person.getName());
//
//        System.out.print(allUserData);
//
//        return allUserData;
//    }

    @GetMapping("/getAllUserData")
    public String getAllUserData(){

        return "Auth In";
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

    //remove user by username
    @DeleteMapping("/remove-user")
    public String removeAUser(@RequestParam int userId){

        if(userId <= 0) return "Invalid";

        System.out.print(userId);

        this.personDetailsService.deletePerson(userId);

        return "deleted";
    }

}
