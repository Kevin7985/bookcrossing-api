package ru.ist.users.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class NewUserDto {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 512)
    private final String login;

    @Email
    @NotNull
    @NotEmpty
    @NotBlank
    private final String email;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 512)
    private final String name;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    private final String password;
}
