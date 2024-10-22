package ru.ist.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ist.exceptions.baseExceptions.ForbiddenException;
import ru.ist.exceptions.model.ApiError;
import ru.ist.users.exception.UserNotFoundException;

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

    @ExceptionHandler({
            UserNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError entityNotFoundExceptionHandler(Exception e) {
        return new ApiError(
                HttpStatus.NOT_FOUND.name(),
                e.getMessage()
        );
    }

    @ExceptionHandler({
            ForbiddenException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError forbiddenExceptionHandler(Exception e) {
        return new ApiError(
                HttpStatus.FORBIDDEN.name(),
                e.getMessage()
        );
    }
}
