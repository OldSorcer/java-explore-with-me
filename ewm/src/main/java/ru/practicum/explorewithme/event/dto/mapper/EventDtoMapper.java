package ru.practicum.explorewithme.event.dto.mapper;

import lombok.AllArgsConstructor;
import ru.practicum.explorewithme.category.dto.CategoryDtoMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.LocationDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventDto;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.user.dto.mapper.UserDtoMapper;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class EventDtoMapper {
    private final StatsClient statsClient;

    public static Event toEvent(NewEventDto newEventDto, User initiator, Category category) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .paid(newEventDto.isPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .title(newEventDto.getTitle())
                .initiator(initiator)
                .category(category)
                .state(EventState.PENDING)
                .createdOn(LocalDateTime.now())
                .lat(newEventDto.getLocation().getLat())
                .lon(newEventDto.getLocation().getLon())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .id(event.getId())
                .eventDate(event.getEventDate())
                .category(CategoryDtoMapper.toCategoryDto(event.getCategory()))
                .description(event.getDescription())
                .initiator(UserDtoMapper.toUserShortDto(event.getInitiator()))
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .createdOn(event.getCreatedOn())
                .state(event.getState())
                .requestModeration(event.isRequestModeration())
                .paid(event.isPaid())
                .title(event.getTitle())
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getViews())
                .location(new LocationDto(event.getLat(), event.getLon()))
                .build();
    }

    public static List<EventFullDto> toEventFullDto(List<Event> events) {
        return events.stream()
                .map(EventDtoMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryDtoMapper.toCategoryDto(event.getCategory()))
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserDtoMapper.toUserShortDto(event.getInitiator()))
                .title(event.getTitle())
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getViews())
                .paid(event.isPaid())
                .build();
    }

    public static List<EventShortDto> toEventShortDto(List<Event> events) {
        return events.stream()
                .map(EventDtoMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    public static Event updateEventDtoToEvent(UpdateEventDto updateEventDto, Event event) {
        if (Objects.nonNull(updateEventDto.getAnnotation())) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }
        if (Objects.nonNull(updateEventDto.getDescription())) {
            event.setDescription(updateEventDto.getDescription());
        }
        if (Objects.nonNull(updateEventDto.getEventDate())) {
            event.setEventDate(updateEventDto.getEventDate());
        }
        if (Objects.nonNull(updateEventDto.getPaid())) {
            event.setPaid(updateEventDto.getPaid());
        }
        if (Objects.nonNull(updateEventDto.getParticipantLimit())) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }
        if (Objects.nonNull(updateEventDto.getTitle())) {
            event.setTitle(updateEventDto.getTitle());
        }
        return event;
    }
}
