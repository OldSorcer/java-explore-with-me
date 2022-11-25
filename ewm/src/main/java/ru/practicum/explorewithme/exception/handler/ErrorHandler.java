package ru.practicum.explorewithme.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEntityNotFoundException(EntityNotFoundException exc) {
        return ApiError.builder()
                .message(exc.getMessage())
                .reason(exc.getReason())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(exc.getTimestamp())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(ConflictException exc) {
        return ApiError.builder()
                .message(exc.getMessage())
                .reason(exc.getReason())
                .status(HttpStatus.CONFLICT)
                .timestamp(exc.getTimestamp())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletRequestParameterException(MissingServletRequestParameterException exc) {
        return ApiError.builder()
                .message(exc.getMessage())
                .reason(String.format("Отсутствует обязательный параметр: %s", exc.getParameterName()))
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {
        return ApiError.builder()
                .errors(exc.getFieldErrors().stream().map(Object::toString).collect(Collectors.toList()))
                .message(exc.getMessage())
                .reason(exc.getParameter().getParameterName())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleUnsupportedError(Exception exc) {
        return ApiError.builder()
                .message("Server error")
                .reason(exc.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .build();

    }
}