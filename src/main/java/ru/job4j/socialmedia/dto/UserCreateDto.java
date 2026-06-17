package ru.job4j.socialmedia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDto(

        @NotBlank(message = "username must not be blank")
        @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
        String username,

        @NotBlank(message = "email must not be blank")
        @Email(message = "email must be valid")
        @Size(max = 50, message = "email must be less than 50 characters")
        String email,

        @NotBlank(message = "password must not be blank")
        @Size(min = 6, max = 50, message = "password must be between 6 and 50 characters")
        String password
) {
}
