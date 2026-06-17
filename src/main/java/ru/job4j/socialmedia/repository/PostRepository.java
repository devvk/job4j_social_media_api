package ru.job4j.socialmedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByAuthorIdOrderByCreatedAtDesc(Long authorId);

    List<Post> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Modifying
    @Transactional
    @Query("""
            UPDATE Post post
            SET post.title = :title, post.body = :body
            WHERE post.id = :id
            """)
    int updatePost(@Param("id") Long id, @Param("title") String title, @Param("body") String body);

    @Modifying
    @Transactional
    @Query("""
           DELETE FROM Post post
           WHERE post.id = :postId
           """)
    int deleteByPostId(@Param("postId") Long postId);

    @Query("""
            SELECT post
            FROM Post post
            WHERE post.author.id IN (
                SELECT subscription.subscriber.id
                FROM Subscription subscription
                WHERE subscription.user.id = :userId
            )
            ORDER BY post.createdAt DESC
            """)
    Page<Post> findAllSubscriberPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    List<Post> findByAuthorIdIn(List<Long> userIds);
}
