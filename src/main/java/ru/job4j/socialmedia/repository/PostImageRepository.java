package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
