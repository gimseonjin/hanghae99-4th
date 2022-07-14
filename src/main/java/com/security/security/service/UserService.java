package com.security.security.service;

import com.security.security.model.Authority;
import com.security.security.model.User;
import com.security.security.repository.AuthorityRepository;
import com.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException(username));
    }

    public Optional<User> findUser(String email) {
        return userRepository.findByUsername(email);
    }

    public User save(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        Authority defaultAuthority = authorityRepository.findByAuthority("USER")
                .orElse(Authority.builder().authority("USER").build());

        Set<Authority> auth = Set.of(defaultAuthority);

        user.setAuthorities(auth);

        return userRepository.save(user);
    }

}
