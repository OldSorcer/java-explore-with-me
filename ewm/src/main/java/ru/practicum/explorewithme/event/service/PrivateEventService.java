package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventDto;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.participation.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.participation.model.Participation;
import ru.practicum.explorewithme.participation.repository.ParticipationRepository;

import java.util.List;

public interface PrivateEventService {
    EventFullDto create(NewEventDto event, long userId);
    EventFullDto update(UpdateEventDto event, long userId);
    List<EventFullDto> getByInitiatorId(long initiatorId, int from, int size);
    EventFullDto getByInitiatorAndEventId(long initiatorId, long eventId);
    EventFullDto cancelEvent(long initiatorId, long eventId);
    List<ParticipationRequestDto> getRequests(long initiatorId, long eventId);
    ParticipationRequestDto acceptRequest(long initiatorId, long eventId, long requestId);
    ParticipationRequestDto rejectRequest(long initiatorId, long eventId, long requestId);
}
