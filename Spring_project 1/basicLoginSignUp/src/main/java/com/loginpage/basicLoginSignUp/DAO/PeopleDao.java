package com.loginpage.basicLoginSignUp.DAO;

import com.loginpage.basicLoginSignUp.entity.Authorities;
import com.loginpage.basicLoginSignUp.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;


@Repository
public class PeopleDao implements PeopleRepository{

    @Autowired
    EntityManager entityManager;
    @Override
    @Transactional
    public void addAPerson(Person person) {
        this.entityManager.persist(person);
    }

    @Override
    @Transactional
    public void addARoll(Authorities authorities) {
        this.entityManager.persist(authorities);
    }

    @Override
    public Authorities getAuthorityByName(String name) {

        TypedQuery<Authorities> query = this.entityManager.createQuery(
                "SELECT a FROM Authorities a WHERE a.authority = :authName", Authorities.class
        );

        query.setParameter("authName", name);

        return query.getSingleResult();
    }

    @Override
    public Person getPersonByName(String name) {

        TypedQuery<Person> query =  this.entityManager.createQuery("SELECT p FROM Person p " +
                "WHERE p.name = :pName", Person.class);

        query.setParameter("pName", name);

        return query.getSingleResult();

    }


    //    the only way you can update a composite primary key
    @Override
    @Transactional
    public void updateTheRolesOfAUser(String name, String authorities) {

        // get the person in the db
        Person person = getPersonByName(name);

        // get the authority from the db
        Authorities authorities1 = getAuthorityByName(authorities);

        // combine them
        person.addRoles(authorities1);

        //persist them
        entityManager.persist(person);

    }

    @Override
    @Transactional
    public boolean addNewUserWithAnExistingRoll(Person person, Collection<String> roles) throws EmptyResultDataAccessException, DataIntegrityViolationException, SQLIntegrityConstraintViolationException {

        //find the rolls in db only then can you get a composite persist
        roles.forEach(a ->  person.addRoles(getAuthorityByName(a)));

        if(person.getAuthorities() == null) return false;

        entityManager.persist(person);

        return true;
    }


}
