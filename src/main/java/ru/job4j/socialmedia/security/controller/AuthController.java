package ru.job4j.socialmedia.security.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.socialmedia.security.dto.JwtResponseDto;
import ru.job4j.socialmedia.security.dto.LoginRequestDto;
import ru.job4j.socialmedia.security.dto.MessageResponseDto;
import ru.job4j.socialmedia.security.dto.SignupRequestDto;
import ru.job4j.socialmedia.security.jwt.JwtUtils;
import ru.job4j.socialmedia.security.service.AuthService;
import ru.job4j.socialmedia.security.userdetails.UserDetailsImpl;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDto> registerUser(@Valid @RequestBody SignupRequestDto dto) {
        authService.signUp(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponseDto("User registered successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var roles = Objects.requireNonNull(userDetails).getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok(new JwtResponseDto(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
    }
}
