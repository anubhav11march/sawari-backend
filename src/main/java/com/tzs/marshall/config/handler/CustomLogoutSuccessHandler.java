package com.tzs.marshall.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    private final static Logger log = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    public CustomLogoutSuccessHandler () { super(); }

    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        final String refererUrl = request.getHeader("Referer");
        log.info("Logging out to referer URL: " + refererUrl);
        String jsonPayload = "{\"isLoggedOut\" : \"%s\", \"timestamp\" : \"%s\"}";
        String successMessage = String.format(jsonPayload, authentication.isAuthenticated(), Calendar.getInstance().getTime());
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().append(successMessage);
//        super.onLogoutSuccess(request, response, authentication);
    }


}
