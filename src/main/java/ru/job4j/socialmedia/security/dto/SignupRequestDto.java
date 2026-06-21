package ru.job4j.socialmedia.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequestDto(

    @NotBlank(message = "username must not be blank")
    @Size(min = 3, max = 20, message = "username size must be between 3 and 20")
    String username,

    @NotBlank(message = "email must not be blank")
    @Size(max = 50, message = "email size must be 50 or less")
    @Email(message = "email must be valid")
    String email,

    @NotBlank(message = "password must not be blank")
    @Size(min = 6, max = 40, message = "password size must be between 6 and 40")
    String password
) {

        }
