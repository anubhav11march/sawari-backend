package com.tzs.marshall.config.security;

import com.tzs.marshall.config.handler.CustomAccessDeniedHandler;
import com.tzs.marshall.config.handler.CustomAuthenticationFailureHandler;
import com.tzs.marshall.config.handler.CustomAuthenticationSuccessHandler;
import com.tzs.marshall.config.handler.CustomLogoutSuccessHandler;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.service.AuthorPreLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfiguration {

    @Autowired
    private AuthorPreLoginService authorPreLoginService;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfiguration() {
        super();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(daoAuthenticationProvider())
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Uncomment this to enable config
        http
                .authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole(Constants.ROLE_ADMIN)
                .antMatchers("/driver/**").hasAnyRole(Constants.ROLE_DRIVER)
                .antMatchers("/user/**").hasAnyAuthority(Constants.ROLE_USER)
                .antMatchers("/", "/hnf/**", "/index.html", "/login", "/HTWF/**", "/images/**", "/pages/**", "/scripts/**", "/**/fevicon.ico", "/skin.css", "/signup", "/init/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
//                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .failureHandler(authenticationFailureHandler())
                .usernameParameter("username")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler())
                .logoutSuccessUrl("/login?logout=true");

        //Comment this block to bypass config
       /* http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll();*/

        return http.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(authorPreLoginService);
        return provider;
    }
}
