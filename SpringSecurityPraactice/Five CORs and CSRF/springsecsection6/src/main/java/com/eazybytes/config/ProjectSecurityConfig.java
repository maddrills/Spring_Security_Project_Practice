package com.eazybytes.config;

import com.eazybytes.filter.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        //the below makes a CSRF token
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        //we set the attribute with a csrf token
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.
                // if you dont have securityContext then spring will follow the default spring security procedure of creating Jsession id for the spring login page
                securityContext((context) -> context
                        //The requireExplicitSave property in Spring Security controls whether the SecurityContext is automatically saved to the HttpSession after each request. By default, this property is set to false, which means that the SecurityContext is only saved to the HttpSession when it is explicitly saved by calling the SecurityContextHolder.getContext().setAuthentication() method.
                        //telling the framework to save the authentication details
                        .requireExplicitSave(false))
//               //this tells the Session id generated by Spring security to be sent to the UI application for feature subsequent requests
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                //CROs config
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        //the allowed origins we permit
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        //Like GET POST PUT DELETE etc
                        config.setAllowedMethods(Collections.singletonList("*"));
                        //says that the above are allowed to proceed
                        //method in Spring Security allows you to specify whether user credentials are supported
                        config.setAllowCredentials(true);
                        //here if we have http headers that have authorisations we can allow them here
                        //corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
                        //This code configures CORS to allow the "Content-Type" and "Authorization" headers in the CORS request.
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        //Configure how long, in seconds, the response from a pre-flight request can be cached by clients.
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                //spring doesn't offer CSRF protection for GET methods
                //By Default it will stop all Post Put Delete operations from happening
                //remember the csrf cooke can only be processed by the UI app Running on your Browser
                //and your browser will only send netflix cooke to netflix
                //so in a usual situation you will send both a session and a CSRF. the CSRF cooke will be modified to be sent under a different header name agreed by you backend
                // if a hacker does a CSRF attack he will have to modify this CSRF Cooke... since the browser wont give him in general he cant take the cooke there is nothing he can do
                //request handler comes from line 28
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact", "/register")
                        // inside CookieCsrfTokenRepository
                        /**
                            static final String DEFAULT_CSRF_COOKIE_NAME = "XSRF-TOKEN";
                            static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
                            static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";
                         */
                        //withHttpOnlyFalse() tells the ui application that you can process the cooke
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                        // we created a filter here so that on log in will be the only time the UI gets the configured CsrfCookieFilter()
                        //tells spring new CsrfCookieFilter() after  exe BasicAuthenticationFilter.class
                        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
                        .requestMatchers("/notices", "/contact", "/register").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}