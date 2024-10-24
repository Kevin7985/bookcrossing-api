package ru.ist.authors.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewAuthorDto {
    @Size(max = 512)
    private final String name;

    @Size(max = 512)
    private final String surname;

    @Size(max = 512)
    private final String patronymic;
}
