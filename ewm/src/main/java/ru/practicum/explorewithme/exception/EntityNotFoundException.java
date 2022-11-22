package ru.practicum.explorewithme.exception;

public class EntityNotFoundException extends CustomException{
    public EntityNotFoundException(String message, String reason) {
        super(message, reason);
    }
}