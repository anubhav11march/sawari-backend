package com.tzs.marshall.config.handler;

import com.tzs.marshall.bean.PersistentUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final static Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("Authentication Success: " + authentication.isAuthenticated());
        httpServletResponse.setStatus(HttpStatus.OK.value());
        PersistentUserDetails principal = (PersistentUserDetails) authentication.getPrincipal();
        String fullName = principal.getFirstName().concat(" ").concat(principal.getLastName());
        String jsonPayload = "{\"fullName\" : \"%s\", \"isAuthenticated\" : \"%s\", \"timestamp\" : \"%s\", \"role\" : \"%s\", \"userName\" : \"%s\"}";
        String successMessage = String.format(jsonPayload, fullName, authentication.isAuthenticated(), Calendar.getInstance().getTime(),
                authentication.getAuthorities().stream().findFirst().get(), authentication.getName());
        httpServletResponse.getWriter().append(successMessage);
//        httpServletResponse.sendRedirect("/home");
    }
}
