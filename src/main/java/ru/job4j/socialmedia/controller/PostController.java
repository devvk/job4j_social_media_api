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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.PostRequestDto;
import ru.job4j.socialmedia.dto.PostResponseDto;
import ru.job4j.socialmedia.dto.UserPostsResponseDto;
import ru.job4j.socialmedia.service.PostService;

import java.net.URI;
import java.util.List;

@Tag(name = "PostController", description = "PostController management APIs")
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Get post by id", description = "Returns one post by the specified id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post found", content = { @Content(schema = @Schema(implementation = PostResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid post id", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Post not found", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable
                                   @Min(value = 1, message = "id must be greater than 0")
                                   Long id) {
        return postService.findById(id);
    }

    @Operation(summary = "Get all posts", description = "Returns a list of all posts.")
    @ApiResponse(responseCode = "200", description = "Posts found", content = { @Content(schema = @Schema(implementation = PostResponseDto.class), mediaType = "application/json") })
    @GetMapping
    public List<PostResponseDto> getAll() {
        return postService.findAll();
    }

    @Operation(summary = "Create post", description = "Creates a new post for the specified user and returns the created post with Location header.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post created", content = { @Content(schema = @Schema(implementation = PostResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid request data or user id", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema()) })
    })
    @PostMapping("/{userId}")
    public ResponseEntity<PostResponseDto> create(@PathVariable
                                                  @Min(value = 1, message = "userId must be greater than 0")
                                                  Long userId,
                                                  @RequestBody @Valid PostRequestDto dto) {
        PostResponseDto post = postService.create(userId, dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.id())
                .toUri();

        return ResponseEntity.created(uri).body(post);
    }

    @Operation(summary = "Update post", description = "Updates an existing post by the specified id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post updated", content = { @Content(schema = @Schema(implementation = PostResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid request data or post id", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Post not found", content = { @Content(schema = @Schema()) })
    })
    @PutMapping("/{id}")
    public PostResponseDto update(@PathVariable
                                  @Min(value = 1, message = "id must be greater than 0")
                                  Long id,
                                  @RequestBody @Valid PostRequestDto dto) {
        return postService.update(id, dto);
    }

    @Operation(summary = "Delete post", description = "Deletes an existing post by the specified id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Post deleted", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Invalid post id", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Post not found", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable
                                       @Min(value = 1, message = "id must be greater than 0")
                                       Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get posts by users", description = "Returns posts grouped by the specified user ids.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posts found", content = { @Content(schema = @Schema(implementation = UserPostsResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid user ids", content = @Content(schema = @Schema()))
    })
    @GetMapping("/by-users")
    List<UserPostsResponseDto> getPostsByUsers(
            @RequestParam List<@Min(value = 1, message = "userId must be greater than 0") Long> userIds) {
        return postService.findPostsByUsers(userIds);
    }
}
