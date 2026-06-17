package ru.job4j.socialmedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.PostImage;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Post response")
public record PostResponseDto(

        @Schema(description = "Post id", example = "1")
        Long id,

        @Schema(description = "Post title", example = "My first post")
        String title,

        @Schema(description = "Post content", example = "This is my first post.")
        String body,

        @Schema(description = "Creation date", example = "2026-06-17T17:42:33")
        LocalDateTime createdAt,

        @Schema(description = "Image URLs")
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
