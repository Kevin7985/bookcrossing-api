package ru.ist.authors;

import ru.ist.authors.dto.AuthorDto;
import ru.ist.authors.dto.NewAuthorDto;

import java.util.UUID;

public interface AuthorService {
    AuthorDto addAuthor(NewAuthorDto authorDto);
    AuthorDto getAuthorById(UUID authorId);
}
