package com.br.compassuol.sp.challenge.msauth.service.implementation;

import com.br.compassuol.sp.challenge.msauth.model.entity.UserEntity;
import com.br.compassuol.sp.challenge.msauth.repository.UserEntityRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final UserEntityRepository userRepository;

    public CustomUserDetails(UserEntityRepository userEntityRepository) {
        this.userRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username));

        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
