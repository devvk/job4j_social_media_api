package ru.job4j.socialmedia.security.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(

        @NotBlank(message = "username must not be blank")
        String username,

        @NotBlank(message = "password must not be blank")
        String password
) {
}
