package com.security.security.model.teacher;

import com.security.security.model.student.Student;
import com.security.security.model.student.StudentAuthentication;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class TeacherManager implements AuthenticationProvider, InitializingBean {

    HashMap<String, Teacher> inmemoryDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            if (inmemoryDB.containsKey(token.getName())) {
                return getToken(token.getName());
            }
            return null;
        }else{
            TeacherAuthentication token = (TeacherAuthentication) authentication;
            if (inmemoryDB.containsKey(token.getCredentials())) {
                return getToken(token.getCredentials());
            }
        }
        return null;
    }

    private TeacherAuthentication getToken(String id) {
        Teacher teacher = inmemoryDB.get(id);
        return TeacherAuthentication.builder()
                .principal(teacher)
                .details(teacher.getId())
                .authenticated(true)
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == TeacherAuthentication.class
                || authentication == UsernamePasswordAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Teacher(
                        "carry", "Carry", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")), null)
        ).forEach(
                teacher -> inmemoryDB.put(teacher.getId(), teacher)
        );
    }
}

