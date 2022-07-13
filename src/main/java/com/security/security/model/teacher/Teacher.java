package com.security.security.model.teacher;

import com.security.security.model.student.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    private String id;
    private String username;
    private Set<GrantedAuthority> roles;
    private List<Student> studentList;
}