package ru.job4j.socialmedia.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.dto.UserCreateDto;
import ru.job4j.socialmedia.dto.UserResponseDto;
import ru.job4j.socialmedia.dto.UserUpdateDto;
import ru.job4j.socialmedia.exception.EmailAlreadyExistsException;
import ru.job4j.socialmedia.exception.LoginAlreadyExistsException;
import ru.job4j.socialmedia.exception.UserNotFoundException;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::from)
                .toList();
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserResponseDto findById(Long userId) {
        return UserResponseDto.from(getUserById(userId));
    }

    @Transactional
    public UserResponseDto create(UserCreateDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new LoginAlreadyExistsException(dto.username());
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPasswordHash(dto.password());
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return UserResponseDto.from(savedUser);
    }

    @Transactional
    public UserResponseDto update(Long userId, UserUpdateDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException(dto.email());
        }
        User user = getUserById(userId);
        user.setEmail(dto.email());
        user.setPasswordHash(dto.password());
        User updatedUser = userRepository.save(user);
        return UserResponseDto.from(updatedUser);
    }

    @Transactional
    public void delete(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }
}
