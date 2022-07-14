package com.security.security.model.dto.request;

import com.security.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {

    private String username;
    private String password;

    public User toUser(){
        return User.builder()
                .username(username)
                .password(password)
                .authorities(Set.of())
                .enabled(true)
                .build();
    }
}
