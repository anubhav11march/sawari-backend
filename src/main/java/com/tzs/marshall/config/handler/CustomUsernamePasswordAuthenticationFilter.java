package com.tzs.marshall.config.handler;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final static Logger log = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);
    public CustomUsernamePasswordAuthenticationFilter(){
        super();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        if (request.getHeader("Content-Type").equals(MediaType.APPLICATION_JSON.toString())) {

            LoginRequest loginRequest = this.getLoginRequest(request);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                    , loginRequest.getPassword());

            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }

        return super.attemptAuthentication(request, response);
    }

    private LoginRequest getLoginRequest(HttpServletRequest request) {
        BufferedReader reader = null;
        LoginRequest loginRequest = null;
        try {
            reader = request.getReader();
            Gson gson = new Gson();
            loginRequest = gson.fromJson(reader, LoginRequest.class);
        } catch (IOException ex) {
            log.error("CustomUsernamePasswordAuthenticationFilter#getLoginRequest", ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                log.error("CustomUsernamePasswordAuthenticationFilter#getLoginRequest", ex);
            }
        }

        if (loginRequest == null) {
            loginRequest = new LoginRequest();
        }

        return loginRequest;
    }

    @Getter
    @Setter
    private static class LoginRequest {

        String username;
        String password;

    }
}

