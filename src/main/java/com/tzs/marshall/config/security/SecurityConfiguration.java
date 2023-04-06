package com.tzs.marshall.config.security;

import com.tzs.marshall.config.handler.*;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.service.UserPreLoginService;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfiguration {

    @Autowired
    private UserPreLoginService userPreLoginService;

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
                .csrf().disable()
                .authorizeRequests()
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority(Constants.ADMIN)
                .antMatchers("/driver/**").hasAnyAuthority(Constants.DRIVER)
                .antMatchers("/user/**").hasAnyAuthority(Constants.USER)
                .antMatchers("/", "/login","/**/favicon.ico", "/signup", "/init/**", "/css/**", "/images/**", "/js/**", "/webfonts/**", "/tnc.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .addFilterAt(customAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
//                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
//                .defaultSuccessUrl("/home")
//                .failureUrl("/login?error=true")
                .failureHandler(authenticationFailureHandler())
                .usernameParameter("username")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler())
//                .logoutSuccessUrl("/login?logout=true")
                /*.and()
                .sessionManagement().maximumSessions(1).expiredUrl("/login?error=true")*/;

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
        provider.setUserDetailsService(userPreLoginService);
        return provider;
    }

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customAuthenticationFilter(HttpSecurity httpSecurity) throws Exception {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authManager(httpSecurity));
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return filter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://192.168.1.9:3000"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        //the below three lines will add the relevant CORS response headers
        configuration.addAllowedOrigin("http://192.168.1.9:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
