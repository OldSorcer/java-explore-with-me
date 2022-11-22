package ru.practicum.explorewithme.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class CustomException extends RuntimeException{
    private final String reason;
    private final LocalDateTime timestamp;

    public CustomException(String message, String reason) {
        super(message);
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }
}
