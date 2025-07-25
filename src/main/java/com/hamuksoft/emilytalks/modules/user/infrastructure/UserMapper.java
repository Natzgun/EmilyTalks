package com.hamuksoft.emilytalks.modules.user.infrastructure;

import com.hamuksoft.emilytalks.modules.user.domain.*;

public class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException();
    }

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId().getValue());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRoles(user.getRoles());
        return entity;
    }

    public static User toDomain(UserEntity entity) {
        return User.builder()
            .id(new UserId(entity.getId()))
            .username(entity.getUsername())
            .email(entity.getEmail())
            .password(entity.getPassword())
            .roles(entity.getRoles())
            .build();
    }
}
