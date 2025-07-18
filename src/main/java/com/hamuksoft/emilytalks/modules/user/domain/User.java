package com.hamuksoft.emilytalks.modules.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class User {
    private final UserId id;
    private final String username;
    private final String email;
    private final String password;
    private final Set<Role> roles;
}
