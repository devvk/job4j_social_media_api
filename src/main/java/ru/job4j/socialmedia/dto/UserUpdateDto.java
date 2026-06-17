package ru.job4j.socialmedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request for user update")
public record UserUpdateDto(

        @Schema(description = "User login", example = "john")
        @NotBlank(message = "username must not be blank")
        @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
        String username,

        @Schema(description = "User email", example = "user@mail.com")
        @NotBlank(message = "email must not be blank")
        @Email(message = "email must be valid")
        @Size(max = 50, message = "email must be less than 50 characters")
        String email,

        @Schema(description = "User password", example = "secret123")
        @NotBlank(message = "password must not be blank")
        @Size(min = 6, max = 50, message = "password must be between 6 and 50 characters")
        String password
) {
}
