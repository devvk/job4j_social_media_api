package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
