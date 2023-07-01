package com.tzs.marshall.config.handler;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.config.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtil jwtTokenUtil;
    private final static Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("Authentication Success: " + authentication.isAuthenticated());
        httpServletResponse.setStatus(HttpStatus.OK.value());
        PersistentUserDetails principal = (PersistentUserDetails) authentication.getPrincipal();
        String fullName = Objects.nonNull(principal.getLastName()) ? principal.getFirstName().concat(" ").concat(principal.getLastName()) : principal.getFirstName();
        String jsonPayload = "{\"fullName\" : \"%s\", \"isAuthenticated\" : \"%s\", \"timestamp\" : \"%s\", \"role\" : \"%s\", \"userName\" : \"%s\"}";
        String successMessage = String.format(jsonPayload, fullName, authentication.isAuthenticated(), Calendar.getInstance().getTime(),
                authentication.getAuthorities().stream().findFirst().get(), authentication.getName());

        //getJwtToken
        String jwtToken = jwtTokenUtil.generateToken(principal);
        String authorizationToken = "Bearer " + jwtToken;
        httpServletResponse.setHeader("Authorization", authorizationToken);
        httpServletResponse.getWriter().append(successMessage);
//        httpServletResponse.sendRedirect("/home");
    }
}
