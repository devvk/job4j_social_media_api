package ru.job4j.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostRequestDto(

        @NotBlank(message = "title must not be blank")
        String title,

        @NotBlank(message = "text must not be blank")
        String body,

        List<String> images
) {
}
