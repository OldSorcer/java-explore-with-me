package ru.practicum.explorewithme.request.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.validate.EventValidator;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.ForbiddenOperationException;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.request.dto.mapper.ParticipantDtoMapper;
import ru.practicum.explorewithme.request.model.Participation;
import ru.practicum.explorewithme.request.model.ParticipationStatus;
import ru.practicum.explorewithme.request.repository.RequestRepository;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Класс-сервис содержищий основную функциональность по работе с заявками на участие в событии.
 * @see ru.practicum.explorewithme.request.service.PrivateRequestService
 */
@Service
@AllArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public ParticipationRequestDto create(long userId, long eventId) {
        User foundedUser = findUserById(userId);
        Event foundedEvent = findEventById(eventId);
        Optional<Participation> foundedRequest = requestRepository.findByRequesterIdAndEventId(userId, eventId);
        if (foundedRequest.isPresent()) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Запрос на участие в событии от текущего пользователя уже создан");
        }
        Participation request = createRequest(foundedUser, foundedEvent);
        if (request.getStatus().equals(ParticipationStatus.CONFIRMED)) {
            foundedEvent.setConfirmedRequests(foundedEvent.getConfirmedRequests() + 1);
            eventRepository.save(foundedEvent);
        }
        return ParticipantDtoMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getAll(long userId) {
        findUserById(userId);
        List<Participation> participants = requestRepository.findAllByRequesterId(userId);
        return ParticipantDtoMapper.toParticipationRequestDto(participants);
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        User foundedUser = findUserById(userId);
        Participation request = requestRepository.findById(requestId)
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
        return ParticipantDtoMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    /**
     * Метод поиска пользователя по его числовому идентификатору.
     * @param userId числовой идентификатор пользователя.
     * @return объект класса {@link ru.practicum.explorewithme.user.model.User}.
     */
    private User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден",
                        String.format("Пользователь с ID %d не найден", userId)));
    }

    /**
     * Метод поиска события по его числовому идентификатору.
     * @param eventId числовой идентификатор события.
     * @return объект класса {@link ru.practicum.explorewithme.event.model.Event}.
     */
    private Event findEventById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено",
                        String.format("Событие с ID %d не найдено", eventId)));
    }

    /**
     * Метод инициализации необходимых для создания запроса на участие в событие полей класса
     * {@link ru.practicum.explorewithme.request.model.Participation}.
     * @param requester объект класса {@link ru.practicum.explorewithme.user.model.User};
     * @param event объект класса {@link ru.practicum.explorewithme.event.model.Event}.
     * @return объект класса {@link ru.practicum.explorewithme.request.model.Participation}.
     */
    private Participation createRequest(User requester, Event event) {
        if (requester.getId() == event.getInitiator().getId()) {
            throw new ConflictException("Ошибка при создании запроса на участие",
                    "Запрос на участие не может быть создан к собственному событию");
        }
        EventValidator.validateEventState(event);
        LocalDateTime createdOn = LocalDateTime.now();
        Participation.ParticipationBuilder participationBuilder = Participation.builder()
                .requester(requester)
                .event(event)
                .created(createdOn);
        if (event.isRequestModeration()) {
            participationBuilder.status(ParticipationStatus.PENDING);
        } else {
            participationBuilder.status(ParticipationStatus.CONFIRMED);
        }
        return participationBuilder.build();
    }
}
