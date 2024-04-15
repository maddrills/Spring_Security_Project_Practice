package com.matSecurity.firstLesson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class ProjectSecurityConfig {


    //make sure you have no auth selected in postman when using /register post rout
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {


        //for readability //use if you want post method to work
        //http.csrf(csrf -> csrf.disable());
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((requests) -> requests
                        // must be authenticated
                        .requestMatchers("/myAccount","/myBalance","/myLoans","/myCards").authenticated()
                        //.requestMatchers(HttpMethod.POST,"/register").permitAll()
                        //login controller has the logic for register
                        .requestMatchers("/notices","/contact","/register").permitAll())
                //authenticate from any form ui
                .formLogin(Customizer.withDefaults())
                //authenticate any when using JSON or remote FrontEnd Frameworks
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//     @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        //Approach 1 where we use withDefaultPasswordEncoder() method
//		//while creating the user details
//     UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("12345")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("12345")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//
//     //Approach 2 where we use NoOpPasswordEncoder Bean
//		//while creating the user details
//        UserDetails admin = User.withUsername("admin")
//                .password("12345")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password("12345")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//
//    }
//

    // when using db related authentication
//    dont use if you have your custom table its a spring auth stuff
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    // tells spring security that authorise passwords with plane text

    // putting new password encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

}
