package com.alkemy.ong.security.config;

import com.alkemy.ong.security.filter.JwtRequestFilter;
import com.alkemy.ong.security.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsCustomService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/auth/login","/auth/register" ).permitAll() // Any user can access /auth/login and /auth/register
                .antMatchers("/auth/me").hasAuthority("ROLE_ADMIN") // Only Admins can access other locations in /auth/**
                .antMatchers(HttpMethod.GET,
                        "/activities",
                        "/organizations",
                        "/categories",
                        "/news",
                        "/news/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER") // Only authenticated roles can access GET methods
                .antMatchers(HttpMethod.PUT,
                        "/members" + "/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.POST, "/organizations/public/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE).hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/docs/**", "swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/api/swagger-ui/**").permitAll()
                .antMatchers("/activities", "/activities/{id}",
                        "/organizations",
                        "/categories", "/categories/{id}",
                        "/members", "/members/{id}",
                        "/news", "/news/{id}",
                        "/slides", "/slides/{id}",
                        "/testimonials", "/testimonials/{id}",
                        "/contacts", "/contacts/{id}",
                        "/users").hasAuthority("ROLE_ADMIN")
                // Only admins can access other methods
                .antMatchers("/public/**").permitAll() // All users can access endpoints in /public/**
                .anyRequest().authenticated() // Only authenticated users can access the rest of endpoints
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
