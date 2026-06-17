package ru.job4j.socialmedia.dto;

import java.util.List;

public record UserPostsResponseDto(

        Long userId,
        String username,
        List<PostResponseDto> posts
) {
}
