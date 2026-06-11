package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("""
        SELECT subscription
        FROM Subscription subscription
        WHERE subscription.user.id = :userId
        """)
    List<Subscription> findAllSubscribersByUserId(@Param("userId") Long userId);
}
