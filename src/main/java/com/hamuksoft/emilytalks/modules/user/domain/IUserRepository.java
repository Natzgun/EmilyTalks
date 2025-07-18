package com.hamuksoft.emilytalks.modules.user.domain;

import java.util.Optional;

public interface IUserRepository {
    void save(User user);
    Optional<User> findByUsername(String username);
}
