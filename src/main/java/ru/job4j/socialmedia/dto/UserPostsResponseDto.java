package ru.job4j.socialmedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "User posts response")
public record UserPostsResponseDto(

        @Schema(description = "User id", example = "1")
        Long userId,

        @Schema(description = "User login", example = "john")
        String username,

        @Schema(description = "User posts")
        List<PostResponseDto> posts
) {
}
