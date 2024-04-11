package com.loginpage.basicLoginSignUp.Services;

import com.loginpage.basicLoginSignUp.DAO.PeopleRepository;
import com.loginpage.basicLoginSignUp.entity.Authorities;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonDetailsService {

    @Autowired
    PeopleRepository peopleRepository;


    public void addAPerson(Person person){

        //any business logic goes here before entering the entity
        peopleRepository.addAPerson(person);

    }

    public void addARoll(Authorities authorities){
        peopleRepository.addARoll(authorities);
    }

    public Authorities getAllAuthorities(String AuthName){
        return peopleRepository.getAuthorityByName(AuthName);
    }

    public Person getPersonByName(String name){
        return peopleRepository.getPersonByName(name);
    }

    public void addARollToAUser(Authorities authorities, Person person){

        peopleRepository.updateAPerson(person, authorities);

    }

    public void updateAPersonsRoles(String name, String authorities){
        peopleRepository.updateTheRolesOfAUser(name, authorities);
    }
}
