package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void whenSaveUserThenFindById() {
        User user = userRepository.save(TestDataFactory.createUser());
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
    }

    @Test
    void whenFindAllThenReturnAllUsers() {
        User user1 = userRepository.save(TestDataFactory.createUser());
        User user2 = userRepository.save(TestDataFactory.createUser());
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getId).contains(user1.getId(), user2.getId());
    }
}
