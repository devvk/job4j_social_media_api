package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.job4j.socialmedia.model.Subscription;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        subscriptionRepository.deleteAll();
    }

    @Test
    void whenSaveSubscriptionThenFindById() {
        User user = userRepository.save(TestDataFactory.createUser());
        User subscriber = userRepository.save(TestDataFactory.createUser());
        Subscription subscription = subscriptionRepository.save(TestDataFactory.createSubscription(user, subscriber));
        Optional<Subscription> foundSubscription = subscriptionRepository.findById(subscription.getId());
        assertThat(foundSubscription).isPresent();
        assertThat(foundSubscription.get().getId()).isEqualTo(subscription.getId());
    }

    @Test
    void whenFindAllThenReturnAllSubscriptions() {
        User user1 = userRepository.save(TestDataFactory.createUser());
        User subscriber1 = userRepository.save(TestDataFactory.createUser());
        Subscription subscription1 = subscriptionRepository.save(TestDataFactory.createSubscription(user1, subscriber1));
        User user2 = userRepository.save(TestDataFactory.createUser());
        User subscriber2 = userRepository.save(TestDataFactory.createUser());
        Subscription subscription2 = subscriptionRepository.save(TestDataFactory.createSubscription(user2, subscriber2));
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        assertThat(subscriptions).hasSize(2);
        assertThat(subscriptions).extracting(Subscription::getId).contains(subscription1.getId(), subscription2.getId());
    }
}