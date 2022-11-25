package ru.practicum.explorewithme.exception;

public class ConflictException extends CustomException {
    public ConflictException(String message, String reason) {
        super(message, reason);
    }
}
