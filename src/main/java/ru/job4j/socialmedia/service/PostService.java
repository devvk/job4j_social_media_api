package ru.job4j.socialmedia.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.dto.PostRequestDto;
import ru.job4j.socialmedia.dto.PostResponseDto;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.PostImage;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.PostRepository;
import ru.job4j.socialmedia.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::from)
                .toList();
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found!"));
    }

    public PostResponseDto findById(Long postId) {
        return PostResponseDto.from(getPostById(postId));
    }

    @Transactional
    public PostResponseDto create(Long userId, PostRequestDto dto) {
        User author = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(
                "User not found: " + userId));

        Post post = new Post();
        post.setTitle(dto.title());
        post.setBody(dto.body());
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());

        for (String imageUrl : dto.images()) {
            PostImage postImage = new PostImage();
            postImage.setPath(imageUrl);
            postImage.setPost(post);
            post.getImages().add(postImage);
        }

        Post savedPost = postRepository.save(post);
        return PostResponseDto.from(savedPost);
    }

    @Transactional
    public PostResponseDto update(Long postId, PostRequestDto dto) {
        Post post = getPostById(postId);

//        if (!post.getAuthor().getId().equals(userId)) {
//            throw new AccessDeniedException("You are not allowed to update this post");
//        }

        post.setTitle(dto.title());
        post.setBody(dto.body());
        Post updatedPost = postRepository.save(post);

        return PostResponseDto.from(updatedPost);
    }

    @Transactional
    public void delete(Long postId) {
        Post post = getPostById(postId);

//        if (!post.getAuthor().getId().equals(userId)) {
//            throw new AccessDeniedException("You are not allowed to delete this post");
//        }

        postRepository.delete(post);
    }
}
