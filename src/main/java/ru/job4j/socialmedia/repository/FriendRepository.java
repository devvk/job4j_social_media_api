package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
