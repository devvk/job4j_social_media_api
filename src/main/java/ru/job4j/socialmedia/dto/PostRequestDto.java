package ru.job4j.socialmedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Request for creating or updating a post")
public record PostRequestDto(

        @Schema(description = "Post title", example = "My first post")
        @NotBlank(message = "title must not be blank")
        @Size(max = 255, message = "title must be less than 255 characters")
        String title,

        @Schema(description = "Post content", example = "This is my first post.")
        @NotBlank(message = "text must not be blank")
        String body,

        @Schema(description = "List of image URLs", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
        List<String> images
) {
    public PostRequestDto {
        images = images == null ? List.of() : images;
    }
}
