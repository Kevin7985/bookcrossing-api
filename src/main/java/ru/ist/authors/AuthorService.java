package ru.ist.authors;

import ru.ist.authors.dto.AuthorDto;
import ru.ist.authors.dto.NewAuthorDto;

public interface AuthorService {
    AuthorDto addAuthor(NewAuthorDto authorDto);
}
