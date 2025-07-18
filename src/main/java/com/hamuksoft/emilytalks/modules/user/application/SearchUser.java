package com.hamuksoft.emilytalks.modules.user.application;

import com.hamuksoft.emilytalks.modules.user.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchUser {
    private final IUserRepository userRepository;

    public SearchUser(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
