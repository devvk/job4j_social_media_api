package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenSavePostThenFindById() {
        User author = userRepository.save(TestDataFactory.createUser());
        Post post = postRepository.save(TestDataFactory.createPost(author));
        Optional<Post> optionalPost = postRepository.findById(post.getId());
        assertThat(optionalPost).isPresent();
        assertThat(optionalPost.get().getId()).isEqualTo(post.getId());
    }

    @Test
    void whenFindAllThenReturnAllPosts() {
        User author1 = userRepository.save(TestDataFactory.createUser());
        Post post1 = postRepository.save(TestDataFactory.createPost(author1));
        User author2 = userRepository.save(TestDataFactory.createUser());
        Post post2 = postRepository.save(TestDataFactory.createPost(author2));
        List<Post> posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getId).contains(post1.getId(), post2.getId());
    }
}