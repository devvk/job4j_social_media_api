package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.job4j.socialmedia.model.FriendRequest;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FriendRequestRepositoryTest {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        friendRequestRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenSaveFriendRequestThenFindById() {
        User sender = userRepository.save(TestDataFactory.createUser());
        User receiver = userRepository.save(TestDataFactory.createUser());
        FriendRequest friendRequest = friendRequestRepository.save(TestDataFactory.createFriendRequest(sender, receiver));
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findById(friendRequest.getId());
        assertThat(friendRequestOptional).isPresent();
        assertThat(friendRequestOptional.get().getId()).isEqualTo(friendRequest.getId());
    }

    @Test
    void whenFindAllThenReturnAllFriendRequests() {
        User sender1 = userRepository.save(TestDataFactory.createUser());
        User receiver1 = userRepository.save(TestDataFactory.createUser());
        FriendRequest friendRequest1 = friendRequestRepository.save(TestDataFactory.createFriendRequest(sender1, receiver1));
        User sender2 = userRepository.save(TestDataFactory.createUser());
        User receiver2 = userRepository.save(TestDataFactory.createUser());
        FriendRequest friendRequest2 = friendRequestRepository.save(TestDataFactory.createFriendRequest(sender2, receiver2));
        List<FriendRequest> friendRequests = friendRequestRepository.findAll();
        assertThat(friendRequests).hasSize(2);
        assertThat(friendRequests).extracting(FriendRequest::getId).contains(friendRequest1.getId(), friendRequest2.getId());
    }
}