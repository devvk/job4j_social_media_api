package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.UserCreateDto;
import ru.job4j.socialmedia.dto.UserResponseDto;
import ru.job4j.socialmedia.dto.UserUpdateDto;
import ru.job4j.socialmedia.service.UserService;

import java.net.URI;
import java.util.List;

@Tag(name = "UserController", description = "UserController management APIs")
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user by id", description = "Returns one user by the specified id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found", content = { @Content(schema = @Schema(implementation = UserResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid user id", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema()) })
    })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable
                                   @Min(value = 1, message = "id must be greater than 0")
                                   Long id) {
        return userService.findById(id);
    }

    @Operation(summary = "Get all users", description = "Returns a list of all registered users.")
    @ApiResponse(responseCode = "200", description = "Users found", content = { @Content(schema = @Schema(implementation = UserResponseDto.class), mediaType = "application/json") })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.findAll();
    }

    @Operation(summary = "Create user", description = "Creates a new user and returns the created user with Location header.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created", content = { @Content(schema = @Schema(implementation = UserResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(schema = @Schema()))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateDto dto) {
        UserResponseDto user = userService.create(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @Operation(summary = "Update user", description = "Updates an existing user by the specified id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated", content = { @Content(schema = @Schema(implementation = UserResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid request data or user id", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema()) })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable
                                  @Min(value = 1, message = "id must be greater than 0")
                                  Long id,
                                  @RequestBody @Valid UserUpdateDto dto) {
        return userService.update(id, dto);
    }

    @Operation(summary = "Delete user", description = "Deletes an existing user by the specified id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Invalid user id", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema()) })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable
                                       @Min(value = 1, message = "id must be greater than 0")
                                       Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
