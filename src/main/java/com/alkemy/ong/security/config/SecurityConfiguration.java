package com.alkemy.ong.security.config;

import com.alkemy.ong.security.filter.JwtRequestFilter;
import com.alkemy.ong.security.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.file.AccessDeniedException;

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
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/register", "/users/accessdenied").permitAll()
                .antMatchers("/api/docs/**","/api/swagger-ui/**","/v3/api-docs/**","/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.POST, "/activities").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/activities/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/news").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/slides").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/testimonials").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/activities").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/categories/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/news/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/organization/public").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/slides/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/testimonials/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/categories/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/comments").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/comments/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/updateRoleUser/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/contacts").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/members").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/slides").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/slides/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categories/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/members/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/news/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/slides/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/testimonials/{id}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/users/accessdenied")
                .and().exceptionHandling().accessDeniedPage("/users/accessdenied")
                .and().sessionManagement(). sessionCreationPolicy(STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
