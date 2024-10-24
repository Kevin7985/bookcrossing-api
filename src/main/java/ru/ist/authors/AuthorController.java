package ru.ist.authors;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ist.authors.dto.AuthorDto;
import ru.ist.authors.dto.NewAuthorDto;

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
}
