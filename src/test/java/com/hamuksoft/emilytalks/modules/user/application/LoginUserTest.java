package com.hamuksoft.emilytalks.modules.user.application;
import com.hamuksoft.emilytalks.modules.user.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class LoginUserTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUser loginUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUserNotFound() {
        String username = "testuser";
        String rawPassword = "password";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> loginUser.execute(username, rawPassword));
    }

    @Test
    void testLoginUserInvalidPassword() {
        String username = "testuser";
        String rawPassword = "password";
        User user = User.builder()
                .id(new UserId(UUID.randomUUID().toString()))
                .username(username)
                .password("encodedPassword")
                .build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, user.getPassword())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> loginUser.execute(username, rawPassword));
    }

    @Test
    void testLoginUserSuccess() {
        String username = "testuser";
        String rawPassword = "password";
        User user = User.builder()
                .id(new UserId(UUID.randomUUID().toString()))
                .username(username)
                .password("encodedPassword")
                .build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, user.getPassword())).thenReturn(true);

        loginUser.execute(username, rawPassword);
    }
}