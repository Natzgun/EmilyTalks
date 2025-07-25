package com.hamuksoft.emilytalks.modules.user.infrastructure;

import com.hamuksoft.emilytalks.modules.user.domain.User;
import com.hamuksoft.emilytalks.modules.user.domain.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaUserRepositoryTest {

    @Mock
    private ISpringDataUserRepository repository;

    @InjectMocks
    private JpaUserRepository jpaUserRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        User user = User.builder()
                .id(new UserId("id"))
                .username("username")
                .email("email")
                .password("password")
                .roles(Set.of())
                .build();
        UserEntity userEntity = UserMapper.toEntity(user);

        jpaUserRepository.save(user);

        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(repository).save(userEntityCaptor.capture());
        assertEquals(userEntity, userEntityCaptor.getValue());
    }

    @Test
    void findByUsername() {
        String username = "username";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        when(repository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        Optional<User> result = jpaUserRepository.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }
}