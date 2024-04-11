package com.loginpage.basicLoginSignUp.DAO;

import com.loginpage.basicLoginSignUp.entity.Authorities;
import com.loginpage.basicLoginSignUp.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Transient;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;


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
    @Transactional
    public void updateAPerson(Person person, Authorities authorities) {

        person.addRoles(authorities);

        System.out.println("Auth here look");

        person.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));

        this.entityManager.merge(person);

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

//        get the person in the db
        Person person = getPersonByName(name);

        // get the authority from the db
        Authorities authorities1 = getAuthorityByName(authorities);

        // combine them
        person.addRoles(authorities1);

        //persist them
        entityManager.persist(person);

    }




}
