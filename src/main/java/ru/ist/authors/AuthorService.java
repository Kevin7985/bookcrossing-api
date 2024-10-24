package ru.ist.authors;

import ru.ist.authors.dto.AuthorDto;
import ru.ist.authors.dto.NewAuthorDto;
import ru.ist.utils.model.Response;

import java.util.UUID;

public interface AuthorService {
    AuthorDto addAuthor(NewAuthorDto authorDto);
    Response<AuthorDto> searchAuthors(Integer from, Integer size);
    AuthorDto getAuthorById(UUID authorId);
    void deleteAuthorById(UUID authorId);
}
