package ru.job4j.socialmedia.dto;

import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;

public record UserResponseDto(

        Long id,
        String username,
        String email,
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
