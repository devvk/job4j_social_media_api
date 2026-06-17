package ru.job4j.socialmedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;

@Schema(description = "User response")
public record UserResponseDto(

        @Schema(description = "User id", example = "1")
        Long id,

        @Schema(description = "User login", example = "john")
        String username,

        @Schema(description = "User email", example = "user@mail.com")
        String email,

        @Schema(description = "Creation date", example = "2026-06-17T17:42:33")
        LocalDateTime createdAt
) {
        public static UserResponseDto from(User user) {
            return new UserResponseDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getCreatedAt()
            );
        }
}
