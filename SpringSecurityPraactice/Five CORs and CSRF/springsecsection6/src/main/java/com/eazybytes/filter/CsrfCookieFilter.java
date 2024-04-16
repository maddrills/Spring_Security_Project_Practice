package com.eazybytes.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//OncePerRequestFilter so that its only fired once
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //v63 6:50
        //reading the HTTP request
        //reads the token initially created by the backend application which will be available as a request attribute
        //and casting the request to CSRF token
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if(null != csrfToken.getHeaderName()){
            //set the http header to have
            //void setHeader(String var1, String var2);
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }
        //spring will automatically make this into cooke
        filterChain.doFilter(request, response);
    }

}
