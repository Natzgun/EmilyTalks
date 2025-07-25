package com.hamuksoft.emilytalks.modules.user.application;

import com.hamuksoft.emilytalks.modules.user.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RegisterUserTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUser registerUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserRegisteredSuccessfully() {
        String username = "testuser";
        String email = "testuser@example.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";
        Set<Role> roles = Set.of();

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        User result = registerUser.execute(username, email, rawPassword, roles);

        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals(roles, result.getRoles());
    }

    @Test
    void testRegisterUserAlreadyExists() {
        String username = "testuser";
        String email = "testuser@example.com";
        String rawPassword = "password";
        Set<Role> roles = Set.of();

        User existingUser = User.builder()
                .id(new UserId(UUID.randomUUID().toString()))
                .username(username)
                .email(email)
                .password("existingPassword")
                .roles(roles)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        assertThrows(IllegalArgumentException.class, () -> registerUser.execute(username, email, rawPassword, roles));
    }
}