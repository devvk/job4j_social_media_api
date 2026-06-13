package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.FriendRequest;
import ru.job4j.socialmedia.model.User;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    boolean existsBySenderAndReceiver(User sender, User receiver);

    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);
}
