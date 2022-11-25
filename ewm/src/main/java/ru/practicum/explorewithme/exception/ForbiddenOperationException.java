package ru.practicum.explorewithme.exception;

import ru.practicum.explorewithme.exception.CustomException;

public class ForbiddenOperationException extends CustomException {
    public ForbiddenOperationException (String message, String reason) {
        super(message, reason);
    }
}
