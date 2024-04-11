package com.loginpage.basicLoginSignUp.DAO;

import com.loginpage.basicLoginSignUp.entity.Authorities;
import com.loginpage.basicLoginSignUp.entity.Person;

public interface PeopleRepository {

    public void addAPerson(Person person);

    public Person getPersonByName(String name);

    public void addARoll(Authorities authorities);

    public Authorities getAuthorityByName(String name);

    public void updateAPerson(Person person, Authorities authorities);

//    the only way you can update a composite primary key
    public void updateTheRolesOfAUser(String name, String authorities);
}
