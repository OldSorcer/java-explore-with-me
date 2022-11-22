package ru.practicum.explorewithme.participation.dto.mapper;

import ru.practicum.explorewithme.participation.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.participation.model.Participation;

import java.util.List;
import java.util.stream.Collectors;

public class ParticipantMapper {
    public static ParticipationRequestDto toParticipationRequestDto (Participation participation) {
        return ParticipationRequestDto.builder()
                .id(participation.getId())
                .event(participation.getEvent().getId())
                .requester(participation.getRequester().getId())
                .status(participation.getStatus())
                .created(participation.getCreated())
                .build();
    }

    public static List<ParticipationRequestDto> toParticipationRequestDto(List<Participation> participants) {
        return participants.stream().map(ParticipantMapper::toParticipationRequestDto).collect(Collectors.toList());
    }
}
