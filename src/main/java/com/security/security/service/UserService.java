package com.security.security.service;

import com.security.security.model.Authority;
import com.security.security.model.User;
import com.security.security.model.dto.request.RegisterRequestDto;
import com.security.security.repository.AuthorityRepository;
import com.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException(username));
    }

    public User save(RegisterRequestDto registerRequestDto) throws ValidationException {

        // 회원가입 요청 검증
        validRegisterRequestDto(registerRequestDto);

        // Dto model 변환
        User user = registerRequestDto.toUser();

        // 비밀번호 암호화
        passwordEncoding(user);

        // 기본 권한 - USER 매핑
        addDefaultAuthority(user);

        return userRepository.save(user);
    }

    private void validRegisterRequestDto(RegisterRequestDto registerRequestDto) throws ValidationException {

        if(registerRequestDto.isContainIdInPw())
            throw new ValidationException("아이디가 비빌번호에 포함되어 있습니다.");

        if(!registerRequestDto.isPwEqualToCheckPw())
            throw new ValidationException("두 비밀번호가 일치하지 않습니다.");

        if(userRepository.findByUsername(registerRequestDto.getUsername()).isPresent())
            throw new ValidationException("중복된 유저네임 입니다.");

    }

    private void passwordEncoding(User user){
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
    }

    private void addDefaultAuthority(User user){
        Authority defaultAuthority = Authority.builder()
                .authority("USER").build();

        user.addAuthority(defaultAuthority);
    }
}
