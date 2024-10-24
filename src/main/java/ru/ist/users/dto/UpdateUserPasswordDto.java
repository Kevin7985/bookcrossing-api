package ru.ist.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserPasswordDto {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    private String oldPassword;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    private String newPassword;
}
