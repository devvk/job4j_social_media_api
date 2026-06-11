package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.PostImage;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostImageRepositoryTest {

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postImageRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenSavePostImageThenFindById() {
        User author = userRepository.save(TestDataFactory.createUser());
        Post post = postRepository.save(TestDataFactory.createPost(author));
        PostImage postImage = postImageRepository.save(TestDataFactory.createPostImage(post));
        Optional<PostImage> optionalPostImage = postImageRepository.findById(postImage.getId());
        assertThat(optionalPostImage).isPresent();
        assertThat(optionalPostImage.get().getId()).isEqualTo(postImage.getId());
    }

    @Test
    void whenFindAllThenReturnAllPostImages() {
        User author = userRepository.save(TestDataFactory.createUser());
        Post post = postRepository.save(TestDataFactory.createPost(author));
        PostImage postImage1 = postImageRepository.save(TestDataFactory.createPostImage(post));
        PostImage postImage2 = postImageRepository.save(TestDataFactory.createPostImage(post));
        List<PostImage> postImages = postImageRepository.findAll();
        assertThat(postImages).hasSize(2);
        assertThat(postImages).extracting(PostImage::getId).contains(postImage1.getId(), postImage2.getId());
    }
}