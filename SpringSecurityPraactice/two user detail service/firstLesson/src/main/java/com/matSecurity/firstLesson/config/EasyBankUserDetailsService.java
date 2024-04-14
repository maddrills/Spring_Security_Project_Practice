package com.matSecurity.firstLesson.config;

import com.matSecurity.firstLesson.DAO.CustomerRepository;
import com.matSecurity.firstLesson.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EasyBankUserDetailsService implements UserDetailsService {

    // db related stuff goes here
    @Autowired
    private CustomerRepository customerRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String userName, password;
//        List<GrantedAuthority> authorities;
//        List<Customer> customer = customerRepository.(username);
//        if (customer.size() == 0) {
//            throw new UsernameNotFoundException("User details not found for the user : " + username);
//        } else{
//            userName = customer.get(0).getEmail();
//            password = customer.get(0).getPwd();
//            authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
//        }
//        return new User(userName,password,authorities);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password;
        List<GrantedAuthority> authorities;
        Customer customer = customerRepository.getCustomerByName(username);
        if (customer == null) {
            throw new UsernameNotFoundException("User details not found for the user : " + username);
        } else{
            userName = customer.getEmail();
            password = customer.getPwd();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(customer.getRole()));
            System.out.print("Reached Here");
        }
        return new User(userName,password,authorities);
    }
}
