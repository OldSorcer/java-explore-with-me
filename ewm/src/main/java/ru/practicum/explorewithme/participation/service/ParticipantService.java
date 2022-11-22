package ru.practicum.explorewithme.participation.service;

import ru.practicum.explorewithme.participation.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.participation.model.Participation;

import java.util.List;

public interface ParticipantService {
    ParticipationRequestDto create(long userId, long eventId);
    List<ParticipationRequestDto> getAll(long userId);
    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
