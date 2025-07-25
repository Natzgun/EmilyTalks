package com.hamuksoft.emilytalks.modules.user.application;

import com.hamuksoft.emilytalks.modules.user.domain.IUserRepository;
import com.hamuksoft.emilytalks.modules.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUser {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginUser(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

    }
}
