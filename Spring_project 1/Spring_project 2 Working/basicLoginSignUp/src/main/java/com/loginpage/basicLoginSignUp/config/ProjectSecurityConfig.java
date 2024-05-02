package com.loginpage.basicLoginSignUp.config;

import com.loginpage.basicLoginSignUp.filter.CsrfCookieFilter;
import com.loginpage.basicLoginSignUp.filter.JWTTokenGeneratorFilter;
import com.loginpage.basicLoginSignUp.filter.JWTTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
import java.util.List;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        //the token is generated here
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        // bellow line is used when you are using JWT tokens instead of jSession session keys
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                //now because we aare sending the JWT token to The UI Application in a Header
                //we need to manage it in the CORs config
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        //check CORs and CSRF in Previous commits
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        //the JWT will be sent to UI under Authorization header
                        config.setExposedHeaders(List.of("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))


                //temporarily disabling cross sight resource forgery
                //.csrf(AbstractHttpConfigurer::disable)
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/Sign-up/signup-user","/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

                //token generation after BasicAuthenticationFilter.class
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                //then position the verification filter
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        //only admin can use this rout
                        .requestMatchers( "/user/addUser","/user/remove-user","/user/get-all-users").hasAnyRole("Admin")
                        .requestMatchers("/user/getAllUserData").hasAnyRole("User")
                        //.requestMatchers("/user/**").hasAnyRole("Admin","User")
                        //any one who is authenticated can access /users
                        .requestMatchers("/user").authenticated()
                        //all the rest are open to public
                        .requestMatchers("/login/LoginUser","/Sign-up/signup-user").permitAll()
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
