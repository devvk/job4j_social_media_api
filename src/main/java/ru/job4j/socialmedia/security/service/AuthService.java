package ru.job4j.socialmedia.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.exception.UserAlreadyExistsException;
import ru.job4j.socialmedia.model.Role;
import ru.job4j.socialmedia.model.RoleName;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.UserRepository;
import ru.job4j.socialmedia.security.dto.SignupRequestDto;
import ru.job4j.socialmedia.security.repository.RoleRepository;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void signUp(SignupRequestDto dto) {
        if (userRepository.existsByUsername(dto.username())
                || userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistsException("Username or Email already exists");
        }
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPasswordHash(encoder.encode(dto.password()));
        Role role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("Role not found"));
        user.setRoles(Set.of(role));
        userRepository.save(user);
    }
}
