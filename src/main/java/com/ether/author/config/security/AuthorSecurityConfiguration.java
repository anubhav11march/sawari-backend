package com.ether.author.config.security;

import com.ether.author.config.handler.CustomAccessDeniedHandler;
import com.ether.author.config.handler.CustomAuthenticationFailureHandler;
import com.ether.author.config.handler.CustomLogoutSuccessHandler;
import com.ether.author.constants.Constants;
import com.ether.author.service.AuthorPreLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
@Configuration
//@Profile("!https")
public class AuthorSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthorPreLoginService authorPreLoginService;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthorSecurityConfiguration() {
        super();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Uncomment this to enable config
        http
                .authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_EDITOR, Constants.ROLE_PRE_EDITOR)
                .antMatchers("/author/**").hasAnyAuthority(Constants.ROLE_AUTHOR)
                .antMatchers("/", "/hnf/**", "/index.html", "/login", "/HTWF/**", "/images/**", "/pages/**", "/scripts/**", "/**/fevicon.ico", "/skin.css", "/signup", "/init/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
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
