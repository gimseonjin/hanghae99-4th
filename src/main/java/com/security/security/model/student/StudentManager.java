package com.security.security.model.student;

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
import java.util.stream.Collectors;

@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {

    HashMap<String, Student> inmemoryDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            if (inmemoryDB.containsKey(token.getName())) {
                return getToken(token.getName());
            }
            return null;
        }else{
            StudentAuthentication token = (StudentAuthentication) authentication;
            if (inmemoryDB.containsKey(token.getCredentials())) {
                return getToken(token.getCredentials());
            }
        }
        return null;
    }

    private StudentAuthentication getToken(String id) {
        Student student = inmemoryDB.get(id);
        return StudentAuthentication.builder()
                .principal(student)
                .details(student.getId())
                .authenticated(true)
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == StudentAuthentication.class
                 || authentication == UsernamePasswordAuthenticationToken.class;
    }

    public List<Student> myStudent(String teacherId){
        return inmemoryDB.values().stream().filter(student -> student.getTeacherId().equals(teacherId)).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Student("kim","kimseonjin",Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "carry"),
                new Student("Lee","leeseohyeon",Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "carry"),
                new Student("Jeon","jeonmingyu",Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "carry")
        ).forEach(
                student -> inmemoryDB.put(student.getId(), student)
        );
    }
}
