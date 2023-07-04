package com.tzs.marshall.config.handler;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public CustomLogoutSuccessHandler() {
        super();
    }

    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        final String refererUrl = request.getHeader("Referer");
        log.info("Logging out to referer URL: " + refererUrl);
        String jsonPayload = "{\"isLoggedOut\" : \"%s\", \"timestamp\" : \"%s\"}";
        if (authentication != null) {
            String successMessage = String.format(jsonPayload, authentication.isAuthenticated(), Calendar.getInstance().getTime());
            response.setStatus(HttpStatus.SC_OK);
            response.getWriter().append(successMessage);
        } else {
            jsonPayload = String.format(jsonPayload, false, Calendar.getInstance().getTime());
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.getWriter().append(jsonPayload);
        }
//        super.onLogoutSuccess(request, response, authentication);
    }


}
