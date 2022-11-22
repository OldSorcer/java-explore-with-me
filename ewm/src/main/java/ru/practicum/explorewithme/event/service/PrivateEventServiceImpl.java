package ru.practicum.explorewithme.event.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventDto;
import ru.practicum.explorewithme.event.dto.mapper.EventDtoMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.validate.EventValidator;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.handler.ForbiddenOperationException;
import ru.practicum.explorewithme.participation.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.participation.dto.mapper.ParticipantMapper;
import ru.practicum.explorewithme.participation.model.Participation;
import ru.practicum.explorewithme.participation.model.ParticipationStatus;
import ru.practicum.explorewithme.participation.repository.ParticipationRepository;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;
import static ru.practicum.explorewithme.event.dto.mapper.EventDtoMapper.toEvent;

@Service
@AllArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService{
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ParticipationRepository participationRepository;

    @Override
    public EventFullDto create(NewEventDto eventDto, long userId) {
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден",
                        format("Пользователь с ID %d не найден", userId)));
        Category category = categoryRepository.findById(eventDto.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("Категория не найдена",
                        format("Категория с ID %d не найдена", eventDto.getCategory())));
        Event newEvent = eventRepository.save(toEvent(eventDto, initiator, category));
        EventValidator.validateEventStartTime(newEvent, true);
        return EventDtoMapper.toEventFullDto(newEvent);
    }

    @Override
    public EventFullDto update(UpdateEventDto event, long userId) {
        Event foundedEvent = findEventById(event.getEventId());
        if (foundedEvent.getInitiator().getId() != userId) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно изменить событие добавленное другим пользователем");
        } else if (foundedEvent.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно изменить уже опубликованное событие");
        }
        Event updatedEvent = EventDtoMapper.updateEventDtoToEvent(event, foundedEvent);
        updatedEvent.setState(EventState.PENDING);
        EventValidator.validateEventStartTime(updatedEvent, true);
        return EventDtoMapper.toEventFullDto(eventRepository.save(updatedEvent));
    }

    @Override
    public List<EventFullDto> getByInitiatorId(long initiatorId, int from, int size) {
        Pageable page = PageRequest.of(from, size);
        return EventDtoMapper.toEventFullDto(eventRepository.findByInitiatorId(initiatorId, page));
    }

    @Override
    public EventFullDto getByInitiatorAndEventId(long initiatorId, long eventId) {
        return EventDtoMapper.toEventFullDto(eventRepository.findByIdAndInitiatorId(eventId, initiatorId));
    }

    @Override
    public EventFullDto cancelEvent(long initiatorId, long eventId) {
        Event foundedEvent = findEventById(eventId);
        if (foundedEvent.getInitiator().getId() != initiatorId) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно отменить событие созданное другим пользователем");
        } else if (foundedEvent.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно отменить уже опубликованное событие");
        }
        foundedEvent.setState(EventState.CANCELED);
        return EventDtoMapper.toEventFullDto(eventRepository.save(foundedEvent));
    }

    @Override
    public List<ParticipationRequestDto> getRequests(long initiatorId, long eventId) {
        Event foundedEvent = findEventById(eventId);
        if (foundedEvent.getInitiator().getId() != initiatorId) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно получить заявки на запрос к событию, добавленному другим пользователем");
        }
        return ParticipantMapper.toParticipationRequestDto(participationRepository.findAllByEventId(eventId));
    }

    @Override
    public ParticipationRequestDto acceptRequest(long initiatorId, long eventId, long requestId) {
        Event foundedEvent = findEventById(eventId);
        if (foundedEvent.getInitiator().getId() != initiatorId) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно принимать запросы к событию, добавленному другим пользователем");
        }
        Participation request = participationRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Запрос не найден",
                        String.format("Запрос с ID %d не найден", requestId)));
        if (!request.getStatus().equals(ParticipationStatus.PENDING)) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    String.format("Невозможно подтвердить запрос со статусом %s", request.getStatus()));
        }
        request.setStatus(ParticipationStatus.CONFIRMED);
        foundedEvent.setConfirmedRequests(foundedEvent.getConfirmedRequests() + 1);
        eventRepository.save(foundedEvent);
        return ParticipantMapper.toParticipationRequestDto(participationRepository.save(request));
    }

    @Override
    public ParticipationRequestDto rejectRequest(long initiatorId, long eventId, long requestId) {
        Event foundedEvent = findEventById(eventId);
        if (foundedEvent.getInitiator().getId() != initiatorId) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно отклонять запросы к событию, добавленному другим пользователем");
        }
        Participation request = participationRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Запрос не найден",
                        String.format("Запрос с ID %d не найден", requestId)));
        if (!request.getStatus().equals(ParticipationStatus.PENDING)) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    String.format("Невозможно подтвердить запрос со статусом %s", request.getStatus()));
        }
        request.setStatus(ParticipationStatus.REJECTED);
        eventRepository.save(foundedEvent);
        return ParticipantMapper.toParticipationRequestDto(participationRepository.save(request));
    }

    private Event findEventById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("События не найдено",
                        String.format("Событие с ID %d не найден", eventId)));
    }
}