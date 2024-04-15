package com.matSecurity.firstLesson.config;

import com.matSecurity.firstLesson.DAO.CustomerRepository;
import com.matSecurity.firstLesson.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//to make it a bean available for Spring to use
@Component
public class EasyBankUsernamePWDAuthenticationProvider implements AuthenticationProvider {

    // db related stuff goes here
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //ProviderManager will call this method
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName, password;
        List<GrantedAuthority> authorities;
        Customer customer = customerRepository.getCustomerByName(authentication.getName());
        if (customer == null) {
            throw new UsernameNotFoundException("User details not found for the user : " + authentication.getName());
        } else{
            userName = customer.getEmail();
            password = customer.getPwd();
            authorities = new ArrayList<>();
            //for now just one roll later i will implement a logic to add all the roles for a user
            authorities.add(new SimpleGrantedAuthority(customer.getRole()));
            System.out.print("Reached Here");
        }
        return new UsernamePasswordAuthenticationToken(userName,password,authorities);

    }

    //authentication signature so that the provider manager can know which AuthenticationProvider to use
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
