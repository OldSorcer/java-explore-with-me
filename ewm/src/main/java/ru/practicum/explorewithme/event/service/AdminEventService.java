package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.UpdateEventDto;
import ru.practicum.explorewithme.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    List<EventFullDto> getAll(List<Long> users,
                              List<EventState> states,
                              List<Long> categories,
                              LocalDateTime rangeStart,
                              LocalDateTime rangeEnd,
                              int from,
                              int size);
    EventFullDto update(UpdateEventDto eventDto, long eventId);
    EventFullDto publish(long eventId);
    EventFullDto reject(long eventId);
}
