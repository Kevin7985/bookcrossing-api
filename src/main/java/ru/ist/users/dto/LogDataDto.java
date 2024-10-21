package ru.ist.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogDataDto {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 512)
    private String login;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    private String password;
}
