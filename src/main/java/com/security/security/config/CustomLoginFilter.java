package com.security.security.config;

import com.security.security.model.student.StudentAuthentication;
import com.security.security.model.teacher.TeacherAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    public CustomLoginFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = obtainUsername(request);
        username = (username != null) ? username : "";
        username = username.trim();
        String password = obtainPassword(request);
        password = (password != null) ? password : "";

        String type = request.getParameter("type");

        if(type.equals("student") || type == null){
            StudentAuthentication token = StudentAuthentication.builder()
                    .credentials(username)
                    .build();
            return this.getAuthenticationManager().authenticate(token);
        }else{
            TeacherAuthentication token = TeacherAuthentication.builder()
                    .credentials(username)
                    .build();
            return this.getAuthenticationManager().authenticate(token);
        }
    }
}
