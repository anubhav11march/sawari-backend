
/*
* TO Add session authentication filter managed by Spring-Session-JDBC do the followings:
* Add a dependency in pom.xml
      <dependency>
        <groupId>org.springframework.session</groupId>
        <artifactId>spring-session-jdbc</artifactId>
      </dependency>
* Add @EnableJdbcHttpSession annotation to SpringbootApplication class
* Add filter AFTER UsernamePasswordAuthenticationFilter.class in SecurityConfig class
* Add below application properties
      spring.session.store-type=jdbc
      spring.session.jdbc.initialize-schema=always
* Uncomment below code
* */

/*
package com.tzs.marshall.config.handler;

import com.tzs.marshall.service.UserPreLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomSessionAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserPreLoginService userPreLoginService;
    private final static Logger log = LoggerFactory.getLogger(CustomSessionAuthenticationFilter.class);



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String principalName;
        if ((request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty())
                && session != null && request.isRequestedSessionIdValid()) {
            String id = session.getId();
            long creationTime = session.getCreationTime();
            principalName = this.userPreLoginService.getUsernameBySessionId(id, creationTime);
            if (principalName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userPreLoginService.loadUserByUsername(principalName);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        chain.doFilter(request, response);
    }
}

*/
