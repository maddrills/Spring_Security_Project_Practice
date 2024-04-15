package com.matSecurity.firstLesson.controller;

import com.matSecurity.firstLesson.DAO.CustomerRepository;
import com.matSecurity.firstLesson.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    //im not using a service layer for simplicity
    @Autowired
    private CustomerRepository customerRepository;

    //this should be in the service layer
    @Autowired
    private PasswordEncoder passwordEncoder;

    //@GetMapping("/register")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer){
        ResponseEntity<String> response = null;

        try{
            //hashing password and setting it to customer
            String pwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(pwd);
            customerRepository.saveCustomer(customer);

        }catch (Exception ex){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error persisting" + customer.getEmail());
        }
        return response;
    }
}
