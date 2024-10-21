package ru.ist.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ist.exceptions.model.ApiError;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler({
            DataIntegrityViolationException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError dataIntegrityViolationExceptionHalder(Exception e) {
        return new ApiError(
                HttpStatus.CONFLICT.name(),
                e.getMessage()
        );
    }
}
