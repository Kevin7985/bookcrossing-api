package ru.ist.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDto {
    private final UUID id;

    @Size(min = 1, max = 512)
    private final String login;

    @Email
    private final String email;

    @Size(min = 1, max = 512)
    private final String name;

    private final String about;
    private final String avatar;
}
