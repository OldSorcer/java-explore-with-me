package ru.practicum.explorewithme.request.dto.mapper;

import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.request.model.Participation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Утилитарный класс содержащий методы для преобразования объектов класса
 * {@link ru.practicum.explorewithme.request.model.Participation} в
 * {@link ru.practicum.explorewithme.request.dto.ParticipationRequestDto}.
 * <br> Кроме того, содержит методы для обратного преобразования.
 */
public class ParticipantDtoMapper {
    /**
     * Метод преобразующий объект класса {@link ru.practicum.explorewithme.request.model.Participation} в
     * DTO-объект класса {@link ru.practicum.explorewithme.request.dto.ParticipationRequestDto}.
     * @param participation объект класса запроса на участие в событии.
     * @return DTO-объект запроса на участие в событии.
     */
    public static ParticipationRequestDto toParticipationRequestDto(Participation participation) {
        return ParticipationRequestDto.builder()
                .id(participation.getId())
                .event(participation.getEvent().getId())
                .requester(participation.getRequester().getId())
                .status(participation.getStatus())
                .created(participation.getCreated())
                .build();
    }

    /**
     * Метод преобразующий список объектов класса {@link ru.practicum.explorewithme.request.model.Participation}
     * в список DTO-объектов класса {@link ru.practicum.explorewithme.request.dto.ParticipationRequestDto}.
     * @param participants список объектов класса заявок на участие в событии.
     * @return список DTO-объектов заявок на участие в событии.
     */
    public static List<ParticipationRequestDto> toParticipationRequestDto(List<Participation> participants) {
        return participants.stream().map(ParticipantDtoMapper::toParticipationRequestDto).collect(Collectors.toList());
    }
}
