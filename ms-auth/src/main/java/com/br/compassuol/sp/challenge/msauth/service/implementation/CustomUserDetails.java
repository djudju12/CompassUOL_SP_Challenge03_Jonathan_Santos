package com.br.compassuol.sp.challenge.msauth.service.implementation;

import com.br.compassuol.sp.challenge.msauth.model.entity.UserEntity;
import com.br.compassuol.sp.challenge.msauth.repository.UserEntityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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

        Set<GrantedAuthority> authorities = getGrantedAuthorities(user);

        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    private static Set<GrantedAuthority> getGrantedAuthorities(UserEntity user) {
        return user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
