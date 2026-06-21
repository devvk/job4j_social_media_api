package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT user
            FROM User AS user
            WHERE user.username = :username AND user.passwordHash = :passwordHash
            """)
    Optional<User> findByUsernameAndPasswordHash(
            @Param("username") String username,
            @Param("passwordHash") String passwordHash
    );

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
}
