package com.tzs.marshall.config.handler;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final static Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException ex) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.warn("User: " + authentication.getName() + " attempted to access the protected URL: " + request.getRequestURI());
            String jsonPayload = String.format("{\"User\" : \"%s\", \"timestamp\" : \"%s\"}", authentication.getName(), Calendar.getInstance().getTime());
            response.setStatus(HttpStatus.SC_FORBIDDEN);
            response.getWriter().append(jsonPayload);
        } else {
            log.warn("Unauthorized Acess: " + ex.getLocalizedMessage());
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.getWriter().append("Unauthorized Access: ").append(ex.getLocalizedMessage());
//        response.sendRedirect(request.getContextPath() + "/accessDenied");
        }
    }
}

