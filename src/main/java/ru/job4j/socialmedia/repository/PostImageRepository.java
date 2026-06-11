package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM PostImage image
            WHERE image.id = :imageId AND image.post.id = :postId
            """)
    int deleteByIdAndPostId(@Param("imageId") Long imageId, @Param("postId") Long postId);
}
