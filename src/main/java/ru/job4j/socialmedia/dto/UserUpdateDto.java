package ru.job4j.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto(


        @NotBlank(message = "username must not be blank")
        String username,

        @NotBlank(message = "email must not be blank")
        String email,

        @NotBlank(message = "password must not be blank")
        String password
) {
}
