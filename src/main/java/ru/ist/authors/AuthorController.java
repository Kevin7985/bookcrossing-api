package ru.ist.authors;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.ist.authors.dto.AuthorDto;
import ru.ist.authors.dto.NewAuthorDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Методы для работы с авторами")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public AuthorDto addAuthor(Authentication auth, @RequestBody NewAuthorDto authorDto) {
        return authorService.addAuthor(authorDto);
    }

    @GetMapping("/{authorId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public AuthorDto getAuthorById(Authentication auth, @PathVariable UUID authorId) {
        return authorService.getAuthorById(authorId);
    }

    @DeleteMapping("/{authorId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteAuthorById(Authentication auth, @PathVariable UUID authorId) {
        authorService.deleteAuthorById(authorId);
    }
}
