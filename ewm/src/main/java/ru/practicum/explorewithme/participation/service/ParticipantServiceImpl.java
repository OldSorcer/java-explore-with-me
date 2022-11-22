package ru.practicum.explorewithme.participation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.validate.EventValidator;
import ru.practicum.explorewithme.exception.ConflictException;
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

@Service
@AllArgsConstructor
public class ParticipantServiceImpl implements ParticipantService{
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public ParticipationRequestDto create(long userId, long eventId) {
        User foundedUser = findUserById(userId);
        Event foundedEvent = findEventById(eventId);
        Participation request = createRequest(foundedUser, foundedEvent);
        if (request.getStatus().equals(ParticipationStatus.CONFIRMED)) {
            foundedEvent.setConfirmedRequests(foundedEvent.getConfirmedRequests() + 1);
            eventRepository.save(foundedEvent);
        }
        return ParticipantMapper.toParticipationRequestDto(participationRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getAll(long userId) {
        findUserById(userId);
        List<Participation> participants = participationRepository.findAllByRequesterId(userId);
        return ParticipantMapper.toParticipationRequestDto(participants);
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        User foundedUser = findUserById(userId);
        Participation request = participationRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Запрос на участие не найден",
                        String.format("Запрос на участие в событии с ID %d не найден", requestId)));
        if (foundedUser.getId() != request.getRequester().getId()) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно отменить заявку на участие в событии другого пользователя");
        }
        Event foundedEvent = findEventById(request.getEvent().getId());
        if (request.getStatus().equals(ParticipationStatus.CONFIRMED)) {
            foundedEvent.setConfirmedRequests(foundedEvent.getConfirmedRequests() - 1);
        }
        request.setStatus(ParticipationStatus.CANCELED);
        return ParticipantMapper.toParticipationRequestDto(participationRepository.save(request));
    }

    private User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден",
                        String.format("Пользователь с ID %d не найден", userId)));
    }

    private Event findEventById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено",
                        String.format("Событие с ID %d не найдено", eventId)));
    }

    private Participation createRequest(User requester, Event event) {
        if (requester.getId() == event.getInitiator().getId()) {
            throw new ConflictException("Ошибка при создании запроса на участие",
                    "Запрос на участие не может быть создан к собственному событию");
        }
        EventValidator.validateEventState(event);
        Participation.ParticipationBuilder participationBuilder = Participation.builder();
        LocalDateTime createdOn = LocalDateTime.now();
        participationBuilder.requester(requester);
        participationBuilder.event(event);
        participationBuilder.created(createdOn);
        if (event.isRequestModeration()) {
            participationBuilder.status(ParticipationStatus.PENDING);
        } else {
            participationBuilder.status(ParticipationStatus.CONFIRMED);
        }
        return participationBuilder.build();
    }
}
