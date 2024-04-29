package com.loginpage.basicLoginSignUp.Services;

import com.loginpage.basicLoginSignUp.DAO.PeopleRepository;
import com.loginpage.basicLoginSignUp.entity.Authorities;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

@Service
public class PersonDetailsService {

    @Autowired
    PeopleRepository peopleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    private String encryptor(String planeText){

        return passwordEncoder.encode(planeText);

    }

    public void addAPerson(Person person){
        //sanity check
        if(!personSanityCheck(person)) return;

        //any business logic goes here before entering the entity

        //encrypt and modify user password
        person.setPassword(encryptor(person.getPassword()));

        peopleRepository.addAPerson(person);

    }

    public void addARoll(Authorities authorities){

        authorities.setAuthority("ROLE_"+authorities.getAuthority());

        peopleRepository.addARoll(authorities);
    }

    public Authorities getAllAuthorities(String AuthName){
        return peopleRepository.getAuthorityByName(AuthName);
    }

    public Person getPersonByName(String name){
        return peopleRepository.getPersonByName(name);
    }

    public void updateAPersonsRoles(String name, String authorities){
        peopleRepository.updateTheRolesOfAUser(name, authorities);
    }


    public boolean addAPersonWithAnExistingRole(Person person, Collection<String> auths){

        //sanity check
        if(!personSanityCheck(person)) return false;
        //encrypt and modify user password
        person.setPassword(encryptor(person.getPassword()));

        try{
            return peopleRepository.addNewUserWithAnExistingRoll(person, auths);
        }catch (EmptyResultDataAccessException e){
            System.out.println(" Roll Not Found In db " + e);
            return false;
        }catch (SQLIntegrityConstraintViolationException | DataIntegrityViolationException e){
            System.out.println(" Duplicate Name In DB please enter a new name" + e);
            return false;
        }
    }

    private boolean personSanityCheck(Person person){
        return person.getName() != null && person.getPassword() != null && person.getAge() > 0 && person.getEmail() != null;
    }
}
