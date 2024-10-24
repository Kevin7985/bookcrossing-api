package ru.ist.authors.dto;

import org.springframework.stereotype.Component;
import ru.ist.authors.model.Author;

@Component
public class AuthorMapper {
    public Author toAuthor(NewAuthorDto authorDto) {
        return new Author(
                null,
                authorDto.getName(),
                authorDto.getSurname(),
                authorDto.getPatronymic()
        );
    }

    public AuthorDto toAuthorDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getSurname(),
                author.getPatronymic()
        );
    }
}
