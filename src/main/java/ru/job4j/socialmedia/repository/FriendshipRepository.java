package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
}
