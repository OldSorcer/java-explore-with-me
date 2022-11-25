package ru.practicum.explorewithme.request.dto.mapper;

import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.request.model.Participation;

import java.util.List;
import java.util.stream.Collectors;

public class ParticipantDtoMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Participation participation) {
        return ParticipationRequestDto.builder()
                .id(participation.getId())
                .event(participation.getEvent().getId())
                .requester(participation.getRequester().getId())
                .status(participation.getStatus())
                .created(participation.getCreated())
                .build();
    }

    public static List<ParticipationRequestDto> toParticipationRequestDto(List<Participation> participants) {
        return participants.stream().map(ParticipantDtoMapper::toParticipationRequestDto).collect(Collectors.toList());
    }
}
