package com.loginpage.basicLoginSignUp.filter;

import com.loginpage.basicLoginSignUp.Constents.SecurityConstants;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import io.jsonwebtoken.Jwts;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //at this point the user is authenticated we just have to send the token back
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {

            //get the JWT key from the contents we defined
            // Keys, Jwts  class comes from pom.xml
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            //creating a JWT token
            // issuer issues a jwt token
            //subject can be any value
            String jwt = Jwts.builder().issuer("Mathew Francis").subject("JWT_Token")
                    //building the token
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + 30000000))
                    //signing it with the key we set on line 35
                    .signWith(key).compact();
            //SecurityConstants.JWT_HEADER, in the Constants SecurityConstants folder
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        }
        System.out.println("Intercepted");

        filterChain.doFilter(request, response);
    }

    //only generate if the path is login
    //other words this method will return false for /login
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login/LoginUser");
    }

//    gets the authority's from granted authority which we set in the configuration CustomAuthenticationProvider class
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
