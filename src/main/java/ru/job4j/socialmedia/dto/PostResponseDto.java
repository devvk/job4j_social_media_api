package ru.job4j.socialmedia.dto;

import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String title,
        String body,
        LocalDateTime createdAt
) {
    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getCreatedAt()
        );
    }
}
