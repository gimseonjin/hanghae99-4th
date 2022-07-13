package com.security.security.model.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    private String id;
    private String username;
    private Set<GrantedAuthority> roles;
    private String teacherId;
}
