package com.tzs.marshall.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final static Logger log = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        log.warn("Authentication Failed: " + e.getMessage());
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\"}";
        String errorMessage = String.format(jsonPayload, e.getMessage(), Calendar.getInstance().getTime());
        httpServletResponse.getWriter().append(errorMessage);
//        httpServletResponse.sendRedirect("/login?error=true");
    }
}
