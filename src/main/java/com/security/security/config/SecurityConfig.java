package com.security.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.security.model.dto.response.ErrorResponse;
import com.security.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                ErrorResponse fail = ErrorResponse.builder()
                        .success(false)
                        .result(HttpStatus.UNAUTHORIZED)
                        .message("Login Required")
                        .build(); // Custom error response.
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = objectMapper.writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationConfiguration.getAuthenticationManager());
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationConfiguration.getAuthenticationManager(), userService);
        http
                .csrf().disable()
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests()
                .antMatchers("/account/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint);
        return http.build();
    }

}
@Component
class PasswordEncoder extends BCryptPasswordEncoder{
}