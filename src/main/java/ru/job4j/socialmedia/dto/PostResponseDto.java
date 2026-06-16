package ru.job4j.socialmedia.dto;

import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.PostImage;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        Long id,
        String title,
        String body,
        LocalDateTime createdAt,
        List<String> images
) {
    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getCreatedAt(),
                post.getImages().stream()
                        .map(PostImage::getPath)
                        .toList()
        );
    }
}
