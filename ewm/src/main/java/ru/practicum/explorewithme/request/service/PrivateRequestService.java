package ru.practicum.explorewithme.request.service;

import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {
    ParticipationRequestDto create(long userId, long eventId);

    List<ParticipationRequestDto> getAll(long userId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
