package ru.ist.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private final String error;
    private final String message;
    private final LocalDateTime date;

    public ApiError(String error, String message) {
        this.error = error;
        this.message = message;
        this.date = LocalDateTime.now();
    }
}
