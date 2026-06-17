package ru.job4j.socialmedia.controller;

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

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable
                                   @Min(value = 1, message = "id must be greater than 0")
                                   Long id) {
        return postService.findById(id);
    }

    @GetMapping
    public List<PostResponseDto> getAll() {
        return postService.findAll();
    }

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

    @PutMapping("/{id}")
    public PostResponseDto update(@PathVariable
                                  @Min(value = 1, message = "id must be greater than 0")
                                  Long id,
                                  @RequestBody @Valid PostRequestDto dto) {
        return postService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable
                                       @Min(value = 1, message = "id must be greater than 0")
                                       Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-users")
    List<UserPostsResponseDto> getPostsByUsers(
            @RequestParam List<@Min(value = 1, message = "userId must be greater than 0") Long> userIds) {
        return postService.findPostsByUsers(userIds);
    }
}
