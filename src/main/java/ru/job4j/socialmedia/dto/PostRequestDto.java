package ru.job4j.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PostRequestDto(

        @NotBlank(message = "title must not be blank")
        @Size(max = 255, message = "title must be less than 255 characters")
        String title,

        @NotBlank(message = "text must not be blank")
        String body,

        List<String> images
) {
    public PostRequestDto {
        images = images == null ? List.of() : images;
    }
}
