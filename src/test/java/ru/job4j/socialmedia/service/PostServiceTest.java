package ru.job4j.socialmedia.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.dto.PostRequestDto;
import ru.job4j.socialmedia.dto.PostResponseDto;
import ru.job4j.socialmedia.dto.UserPostsResponseDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.PostImageRepository;
import ru.job4j.socialmedia.repository.PostRepository;
import ru.job4j.socialmedia.repository.TestDataFactory;
import ru.job4j.socialmedia.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @BeforeEach
    void setUp() {
        postImageRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenFindAllThenReturnAllPosts() {
        User user = userRepository.save(TestDataFactory.createUser());
        PostRequestDto request1 = TestDataFactory.createPostRequestDto();
        PostRequestDto request2 = TestDataFactory.createPostRequestDto();
        postService.create(user.getId(), request1);
        postService.create(user.getId(), request2);

        List<PostResponseDto> posts = postService.findAll();

        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(PostResponseDto::title).containsExactlyInAnyOrder(request1.title(), request2.title());
        assertThat(posts).extracting(PostResponseDto::body).containsExactlyInAnyOrder(request1.body(), request2.body());
        assertThat(posts).extracting(PostResponseDto::images).containsExactlyInAnyOrder(request1.images(), request2.images());
    }

    @Test
    void whenFindByIdThenReturnPost() {
        User user = userRepository.save(TestDataFactory.createUser());
        PostRequestDto request = TestDataFactory.createPostRequestDto();
        PostResponseDto savedPost = postService.create(user.getId(), request);

        PostResponseDto foundPost = postService.findById(savedPost.id());

        assertThat(foundPost.id()).isEqualTo(savedPost.id());
        assertThat(foundPost.title()).isEqualTo(savedPost.title());
        assertThat(foundPost.body()).isEqualTo(savedPost.body());
        assertThat(foundPost.images()).containsExactlyElementsOf(savedPost.images());
    }

    @Test
    void whenCreatePostThenReturnSavedPostWithImages() {
        User user = userRepository.save(TestDataFactory.createUser());
        PostRequestDto request = TestDataFactory.createPostRequestDto();

        PostResponseDto response = postService.create(user.getId(), request);

        assertThat(response.id()).isNotNull();
        assertThat(response.title()).isEqualTo(request.title());
        assertThat(response.body()).isEqualTo(request.body());
        assertThat(response.images()).containsExactlyElementsOf(request.images());
    }

    @Test
    void whenUpdatePostThenReturnUpdatedPost() {
        User user = userRepository.save(TestDataFactory.createUser());
        PostRequestDto originalPost = TestDataFactory.createPostRequestDto();
        PostResponseDto savedOriginalPost = postService.create(user.getId(), originalPost);

        PostRequestDto postUpdate = TestDataFactory.createPostRequestDto();
        PostResponseDto updatedPost = postService.update(savedOriginalPost.id(), postUpdate);

        assertThat(updatedPost.id()).isEqualTo(savedOriginalPost.id());
        assertThat(updatedPost.title()).isEqualTo(postUpdate.title());
        assertThat(updatedPost.body()).isEqualTo(postUpdate.body());
        assertThat(updatedPost.images()).containsExactlyElementsOf(postUpdate.images());
    }

    @Test
    void whenDeletePostThenPostIsDeleted() {
        User user = userRepository.save(TestDataFactory.createUser());
        PostRequestDto request = TestDataFactory.createPostRequestDto();
        PostResponseDto response = postService.create(user.getId(), request);

        postService.delete(response.id());

        assertThat(postRepository.findById(response.id())).isEmpty();
        assertThat(postImageRepository.findAllByPostId(response.id())).isEmpty();
    }

    @Test
    void whenGetPostsByUsersThenReturnAllPostsGroupedByUser() {
        User user1 = userRepository.save(TestDataFactory.createUser());
        User user2 = userRepository.save(TestDataFactory.createUser());
        PostRequestDto user1Post1 = TestDataFactory.createPostRequestDto();
        PostRequestDto user1Post2 = TestDataFactory.createPostRequestDto();
        PostRequestDto user2Post1 = TestDataFactory.createPostRequestDto();
        postService.create(user1.getId(), user1Post1);
        postService.create(user1.getId(), user1Post2);
        postService.create(user2.getId(), user2Post1);

        List<UserPostsResponseDto> result = postService.findPostsByUsers(List.of(user1.getId(), user2.getId()));

        assertThat(result).hasSize(2);

        UserPostsResponseDto user1Posts = result.stream()
                .filter(dto -> dto.userId().equals(user1.getId()))
                .findFirst()
                .orElseThrow();

        UserPostsResponseDto user2Posts = result.stream()
                .filter(dto -> dto.userId().equals(user2.getId()))
                .findFirst()
                .orElseThrow();

        assertThat(user1Posts.username()).isEqualTo(user1.getUsername());
        assertThat(user1Posts.posts()).hasSize(2);
        assertThat(user1Posts.posts())
                .extracting(PostResponseDto::title)
                .containsExactlyInAnyOrder(user1Post1.title(), user1Post2.title());

        assertThat(user2Posts.username()).isEqualTo(user2.getUsername());
        assertThat(user2Posts.posts()).hasSize(1);
        assertThat(user2Posts.posts())
                .extracting(PostResponseDto::title)
                .containsExactly(user2Post1.title());
    }
}