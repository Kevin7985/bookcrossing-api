package ru.ist.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserLoginDto {
    private final UUID userId;
    private final String token;
    private final Long expires;
}
