package com.cocarius.security.Config;


import com.cocarius.security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.cocarius.security.role.Permission.*;
import static com.cocarius.security.role.Role.*;

/**
 * @author LuongTDT
 */
@Configuration
//What are differences between @EnableGlobalMethodSecurity and @EnableWebSecurity?
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true, //enables @Secured annotation.
        jsr250Enabled = true, //enables @RolesAllowed annotation.
        prePostEnabled = true //enables @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter annotations.
)
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    private final AuthenticationProvider authenticationProvider;
    @Autowired
    public WebSecurityConfig(JwtTokenFilter jwtTokenFilter, AuthenticationProvider authenticationProvider) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.authenticationProvider = authenticationProvider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http.cors().and().csrf().disable()
                // Set session management to stateless
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Set permissions on endpoints
                .authorizeRequests()
                // Our public endpoints
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/v*/auth/**").permitAll()
                .antMatchers( "/api/v*/users").hasAnyRole(ADMIN.name(),USER.name(),ADMIN.name())
                .antMatchers( "/api/v*/admin").hasRole(ADMIN.name())
                .antMatchers(HttpMethod.GET,"/api/v*/admin").hasAuthority(ADMIN_READ.getPermission())
                .antMatchers(HttpMethod.PUT,"/api/v*/admin").hasAuthority(ADMIN_UPDATE.getPermission())
                .antMatchers(HttpMethod.POST,"/api/v*/admin").hasAuthority(ADMIN_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE,"/api/v*/admin").hasAuthority(ADMIN_DELETE.getPermission())
                .antMatchers( "/api/v*/management/**").hasAnyRole(ADMIN.name(),MANAGER.name())
                .antMatchers(HttpMethod.GET,"/api/v*/management/**").hasAuthority(MANAGER_READ.getPermission())
                .antMatchers(HttpMethod.PUT,"/api/v*/management/**").hasAuthority(MANAGER_UPDATE.getPermission())
                .antMatchers(HttpMethod.POST,"/api/v*/management/**").hasAuthority(MANAGER_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE,"/api/v*/management/**").hasAuthority(MANAGER_DELETE.getPermission())
                .antMatchers(HttpMethod.GET,"/api/v*/management/**").hasAuthority(ADMIN_READ.getPermission())
                .antMatchers(HttpMethod.PUT,"/api/v*/management/**").hasAuthority(ADMIN_UPDATE.getPermission())
                .antMatchers(HttpMethod.POST,"/api/v*/management/**").hasAuthority(ADMIN_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE,"/api/v*/management/**").hasAuthority(ADMIN_DELETE.getPermission())
                // Our private endpoints
                .anyRequest().authenticated()
                .and()
                // Add JWT token filter before the ExceptionTranslationFilter in security filter chain
                .addFilterBefore(
                    jwtTokenFilter,//https://stackoverflow.com/questions/59302026/spring-security-why-adding-the-jwt-filter-before-usernamepasswordauthenticatio
                    //ExceptionTranslationFilter.class
                        UsernamePasswordAuthenticationFilter.class
                );

        http.authenticationProvider(authenticationProvider);
        return http.build();
    }
}
