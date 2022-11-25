package ru.practicum.explorewithme.exception;

public class ForbiddenOperationException extends CustomException {
    public ForbiddenOperationException(String message, String reason) {
        super(message, reason);
    }
}
