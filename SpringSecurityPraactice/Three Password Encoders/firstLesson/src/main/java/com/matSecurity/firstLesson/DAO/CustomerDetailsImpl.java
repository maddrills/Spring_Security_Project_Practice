package com.matSecurity.firstLesson.DAO;

import com.matSecurity.firstLesson.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDetailsImpl implements CustomerRepository{

    @Autowired
    EntityManager entityManager;


    @Override
    public Customer getCustomerByName(String email) {

        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c " +
                        "WHERE c.email =:email ",Customer.class
        );

        query.setParameter("email",email);

        Customer customer = query.getSingleResult();

        System.out.println(customer);

        return customer;
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {

        entityManager.persist(customer);

    }
}
