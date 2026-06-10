package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
