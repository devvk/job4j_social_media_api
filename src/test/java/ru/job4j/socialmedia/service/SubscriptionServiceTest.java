package ru.job4j.socialmedia.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.model.FriendRequest;
import ru.job4j.socialmedia.model.FriendRequestStatus;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SubscriptionServiceTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postImageRepository.deleteAll();
        postRepository.deleteAll();
        subscriptionRepository.deleteAll();
        friendshipRepository.deleteAll();
        friendRequestRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenSendFriendRequestThenFriendRequestIsSaved() {
        User sender = userRepository.save(TestDataFactory.createUser());
        User receiver = userRepository.save(TestDataFactory.createUser());

        subscriptionService.sendFriendRequest(sender.getId(), receiver.getId());
        FriendRequest friendRequest = friendRequestRepository.findAll().getFirst();

        assertThat(friendRequest.getSender().getId()).isEqualTo(sender.getId());
        assertThat(friendRequest.getReceiver().getId()).isEqualTo(receiver.getId());
        assertThat(friendRequest.getStatus()).isEqualTo(FriendRequestStatus.PENDING);
    }

    @Test
    void whenAcceptFriendRequestThenFriendshipAndSubscriptionAreSaved() {
        User sender = userRepository.save(TestDataFactory.createUser());
        User receiver = userRepository.save(TestDataFactory.createUser());
        subscriptionService.sendFriendRequest(sender.getId(), receiver.getId());

        FriendRequest friendRequest = friendRequestRepository.findAll().getFirst();
        subscriptionService.acceptFriendRequest(friendRequest.getId(), receiver.getId());
        FriendRequest acceptedFriendRequest = friendRequestRepository.findById(friendRequest.getId()).orElseThrow();

        assertThat(acceptedFriendRequest.getStatus()).isEqualTo(FriendRequestStatus.ACCEPTED);
        assertThat(friendshipRepository.findAll()).hasSize(1);
        assertThat(subscriptionRepository.findAll()).hasSize(2);
    }

    @Test
    void whenRejectFriendRequestThenStatusIsRejected() {
        User sender = userRepository.save(TestDataFactory.createUser());
        User receiver = userRepository.save(TestDataFactory.createUser());
        subscriptionService.sendFriendRequest(sender.getId(), receiver.getId());

        FriendRequest friendRequest = friendRequestRepository.findAll().getFirst();
        subscriptionService.rejectFriendRequest(friendRequest.getId(), receiver.getId());
        FriendRequest rejectedFriendRequest = friendRequestRepository.findById(friendRequest.getId()).orElseThrow();

        assertThat(rejectedFriendRequest.getStatus()).isEqualTo(FriendRequestStatus.REJECTED);
        assertThat(friendshipRepository.findAll()).isEmpty();
        assertThat(subscriptionRepository.findAll()).hasSize(1);
    }

    @Test
    void whenRemoveFriendThenFriendshipIsRemovedAndUserIsUnsubscribed() {
        User sender = userRepository.save(TestDataFactory.createUser());
        User receiver = userRepository.save(TestDataFactory.createUser());
        subscriptionService.sendFriendRequest(sender.getId(), receiver.getId());

        FriendRequest friendRequest = friendRequestRepository.findAll().getFirst();
        subscriptionService.acceptFriendRequest(friendRequest.getId(), receiver.getId());

        subscriptionService.removeFriend(receiver.getId(), sender.getId());

        assertThat(friendshipRepository.findAll()).isEmpty();
        assertThat(subscriptionRepository.findAll()).hasSize(1);
    }
}