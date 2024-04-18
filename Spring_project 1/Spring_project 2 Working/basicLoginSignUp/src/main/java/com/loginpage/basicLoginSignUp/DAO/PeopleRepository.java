package com.loginpage.basicLoginSignUp.DAO;

import com.loginpage.basicLoginSignUp.entity.Authorities;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

public interface PeopleRepository {

    public void addAPerson(Person person);

    public Person getPersonByName(String name);

    public Person getPersonNameAndAuthority(String name);

    public void addARoll(Authorities authorities);

    public Authorities getAuthorityByName(String name);

//    the only way you can update a composite primary key
    public void updateTheRolesOfAUser(String name, String authorities);

    public boolean addNewUserWithAnExistingRoll(Person person, Collection<String> roles) throws EmptyResultDataAccessException, DataIntegrityViolationException, SQLIntegrityConstraintViolationException;
}
