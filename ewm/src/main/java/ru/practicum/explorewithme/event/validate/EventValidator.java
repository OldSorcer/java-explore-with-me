package ru.practicum.explorewithme.event.validate;

import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.exception.ForbiddenOperationException;

import java.time.LocalDateTime;

public class EventValidator {
    private static final long HOURS_TO_EVENT_FOR_CREATE = 2;
    private static final long HOURS_TO_EVENT_FOR_PUBLIC = 1;

    public static void validateEventStartTime(Event event, boolean isCreate) {
        LocalDateTime acceptedTime;
        if (isCreate) {
            acceptedTime = LocalDateTime.now().plusHours(HOURS_TO_EVENT_FOR_CREATE);
        } else {
            acceptedTime = LocalDateTime.now().plusHours(HOURS_TO_EVENT_FOR_PUBLIC);
        }
        if (event.getEventDate().isBefore(acceptedTime)) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    String.format("Время начала события не может быть раньше %s",
                            acceptedTime));
        }
    }

    public static void validateEventState(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Запрос к неопубликованному событию не может быть создан");
        } else if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Лимит участников события превышен");
        }
    }
}
