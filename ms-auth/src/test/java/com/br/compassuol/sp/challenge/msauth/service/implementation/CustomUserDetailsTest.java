package com.br.compassuol.sp.challenge.msauth.service.implementation;

import com.br.compassuol.sp.challenge.msauth.model.entity.UserEntity;
import com.br.compassuol.sp.challenge.msauth.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsTest {

    @Mock
    private UserEntityRepository userRepository;

    @InjectMocks
    private CustomUserDetails customUserDetails;

    private final String USERNAME = "username";
    
    
    @Test
    void loadUserByUsername() {
        //given
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername(USERNAME);
        mockUser.setPassword("");
        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.of(mockUser));

        //when
        UserDetails userDetails = customUserDetails.loadUserByUsername(USERNAME);

        //then
        then(userRepository).should().findByUsername(USERNAME);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USERNAME);
    }
}