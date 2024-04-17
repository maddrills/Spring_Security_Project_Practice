package com.eazybytes.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**The OncePerRequestFilter in Spring guarantees that a filter will only be executed once per request.<br>
 *  However, a filter may be executed more than once per request in some circumstances, such as when:<br>
 *  The filter is on the filter chain more than once<br>
 * The request is dispatched to a different servlet using the request dispatcher<br>
 *<br>
 * This prevents redundant operations and potential conflicts that could arise from multiple executions of the same logic within a single request cycle.
 * The OncePerRequestFilter is a special type of filter in Spring that can be called before or after servlet execution. Some use cases for OncePerRequestFilter include: Spring Security, Asynchronous requests, Applying the filter only in the initial request thread, and Invoking the filter at least once in each additional thread.
 * The OncePerRequestFilter class extends the GenericFilterBean class and implements the following interfaces:
 * Filter, Aware, BeanNameAware, DisposableBean, InitializingBean, EnvironmentAware, EnvironmentCapable, and ServletContextAware.*/
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if(null != csrfToken.getHeaderName()){
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }
        filterChain.doFilter(request, response);
    }

}
