package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.Friendship;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("""
            SELECT friendship
            FROM Friendship friendship
            WHERE friendship.user1.id = :userId OR friendship.user2.id = :userId
            """)
    List<Friendship> findAllFriendshipsByUserId(@Param("userId") Long userId);

    Optional<Friendship> findByUser1AndUser2(User user1, User user2);
}
