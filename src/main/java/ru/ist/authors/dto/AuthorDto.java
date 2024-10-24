package ru.ist.authors.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthorDto {
    private final UUID id;
    private final String name;
    private final String surname;
    private final String patronymic;
}
