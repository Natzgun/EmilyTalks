package com.hamuksoft.emilytalks.modules.user.infrastructure;

import com.hamuksoft.emilytalks.modules.user.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepository implements IUserRepository {

    private final ISpringDataUserRepository repository;

    public JpaUserRepository(ISpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        repository.save(entity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(UserMapper::toDomain);
    }
}
