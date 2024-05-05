package com.loginpage.basicLoginSignUp.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//sends a new csrf token to the front-end per request
public class CsrfCookieFilter extends OncePerRequestFilter {

    //executed after CSRF token generation in filter chain check CORs and CSRF token for further understanding
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("Incide CSRF token filter");
        //org.springframework.security.web.csrf.CsrfTokenJWT Generated
        System.out.print(CsrfToken.class.getName());
        //gets the cookie from the filter chain
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if(null != csrfToken.getHeaderName()){
            //static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";
            //X-XSRF-TOKEN
            System.out.println(csrfToken.getHeaderName());
            //header name will be XSRF-TOKEN
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            System.out.println( csrfToken.getToken());
            System.out.println(csrfToken.getHeaderName());
            System.out.println(response.getHeader(csrfToken.getHeaderName()));
            System.out.println("Token GENERATED -----");
        }
        filterChain.doFilter(request, response);
    }
}
