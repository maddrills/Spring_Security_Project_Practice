package com.matSecurity.firstLesson.DAO;

import com.matSecurity.firstLesson.entity.Customer;
import org.springframework.stereotype.Repository;

public interface CustomerRepository {

    public Customer getCustomerByName(String name);

    public void saveCustomer(Customer customer);
}
