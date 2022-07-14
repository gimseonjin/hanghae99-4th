package com.security.security.model.dto.request;

import com.security.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotNull
    @Size(min = 3)
    @Pattern(regexp="[a-zA-Z1-9]{6,12}", message = "아이디는 최소 3자 이상, 영문과 숫자로만 조합해주세요")
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String password2;

    public boolean isPwEqualToCheckPw(){
        return password.equals(password2);
    }

    public boolean isContainIdInPw(){
        return password.contains(username);
    }

    public User toUser(){
        return User.builder()
                .username(username)
                .password(password)
                .authorities(new ArrayList<>())
                .enabled(true)
                .build();
    }
}
