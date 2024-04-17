package com.loginpage.basicLoginSignUp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {



    @Deprecated
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        return new ProviderManager(daoProvider);
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        //the token is generated here
//        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//        requestHandler.setCsrfRequestAttributeName("_csrf");

        // bellow line is used when you are using JWT tokens instead of session keys
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //temporarily disabling cross sight resource forgery
                .csrf((csrf) -> csrf.disable())

                //here is where the token is generated and returned
                //.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)

                //here is where the roll based authorisation happens
                .authorizeHttpRequests((requests) -> requests
                        //only admin can use this rout
                        .requestMatchers("/user/**").hasAnyRole("Admin")
                        //any one who is authenticated can access /users
                        .requestMatchers("/user").authenticated()
                        //all the rest are open to public
                        .requestMatchers("/login/LoginUser","Sign-up/signup-user").permitAll()
                )
                // redirect to /login if the user is not authenticated  Customizer.withDefaults() enables a security feature using the defaults provided by Spring Security
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // to encode the password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
