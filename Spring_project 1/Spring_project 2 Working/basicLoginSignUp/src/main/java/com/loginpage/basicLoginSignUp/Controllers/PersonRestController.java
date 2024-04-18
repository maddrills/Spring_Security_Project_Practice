package com.loginpage.basicLoginSignUp.Controllers;

import com.loginpage.basicLoginSignUp.DTO.AllUserData;
import com.loginpage.basicLoginSignUp.Services.PersonDetailsService;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class PersonRestController {

    @Autowired
    PersonDetailsService personDetailsService;

    @GetMapping("/getAllUserData")
    public AllUserData getAllUserData(){

        Person person = personDetailsService.getPersonByName("Mathew Francis");

        AllUserData allUserData = new AllUserData(
                person.getAuthorities(),
                person.getEmail(),
                person.getAge(),
                person.getName());

        System.out.print(allUserData);

        return allUserData;
    }
}
