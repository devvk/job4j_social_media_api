package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.job4j.socialmedia.model.Friendship;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        friendshipRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenSaveFriendshipThenFindById() {
        User user1 = userRepository.save(TestDataFactory.createUser());
        User user2 = userRepository.save(TestDataFactory.createUser());
        Friendship friendship = friendshipRepository.save(TestDataFactory.createFriendship(user1, user2));
        Optional<Friendship> friendshipOptional = friendshipRepository.findById(friendship.getId());
        assertThat(friendshipOptional).isPresent();
        assertThat(friendshipOptional.get().getId()).isEqualTo(friendship.getId());
    }

    @Test
    void whenFindAllThenReturnAllFriendships() {
        User user1 = userRepository.save(TestDataFactory.createUser());
        User user2 = userRepository.save(TestDataFactory.createUser());
        Friendship friendship1 = friendshipRepository.save(TestDataFactory.createFriendship(user1, user2));
        User user3 = userRepository.save(TestDataFactory.createUser());
        User user4 = userRepository.save(TestDataFactory.createUser());
        Friendship friendship2 = friendshipRepository.save(TestDataFactory.createFriendship(user3, user4));
        List<Friendship> friendships = friendshipRepository.findAll();
        assertThat(friendships).hasSize(2);
        assertThat(friendships).extracting(Friendship::getId).contains(friendship1.getId(), friendship2.getId());

    }
}