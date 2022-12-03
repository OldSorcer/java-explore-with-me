package ru.practicum.explorewithme.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.UpdateEventDto;
import ru.practicum.explorewithme.event.dto.mapper.EventDtoMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.model.QEvent;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.validate.EventValidator;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.ForbiddenOperationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс описывающий логику работы административной части сервиса по работе с событиями.
 */
@Service
@AllArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;

    @Override
    public List<EventFullDto> getAll(List<Long> users,
                                     List<EventState> states,
                                     List<Long> categories,
                                     LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd,
                                     int from,
                                     int size) {
        BooleanExpression predicate = getPredicate(users, states, categories, rangeStart, rangeEnd);
        Pageable page = PageRequest.of(from, size, Sort.by("id"));
        return EventDtoMapper.toEventFullDto(eventRepository.findAll(predicate, page).getContent());
    }

    @Override
    public EventFullDto update(UpdateEventDto eventDto, long eventId) {
        Event newEvent = findEventById(eventId);
        EventDtoMapper.updateEventDtoToEvent(eventDto, newEvent);
        return EventDtoMapper.toEventFullDto(eventRepository.save(newEvent));
    }

    @Override
    public EventFullDto publish(long eventId) {
        Event foundedEvent = findEventById(eventId);
        EventValidator.validateEventStartTime(foundedEvent, false);
        if (!foundedEvent.getState().equals(EventState.PENDING)) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Опубликовать возможно только событие ожидающее модерации");
        }
        foundedEvent.setState(EventState.PUBLISHED);
        foundedEvent.setPublishedOn(LocalDateTime.now());
        return EventDtoMapper.toEventFullDto(eventRepository.save(foundedEvent));
    }

    @Override
    public EventFullDto reject(long eventId) {
        Event foundedEvent = findEventById(eventId);
        if (!foundedEvent.getState().equals(EventState.PENDING)) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    String.format("Событие уже имеет статус %s", foundedEvent.getState()));
        }
        foundedEvent.setState(EventState.CANCELED);
        return EventDtoMapper.toEventFullDto(eventRepository.save(foundedEvent));
    }

    /**
     * Метод поиска события в репозитории по идентификатору.
     * @param eventId числовой идентификатор.
     * @return найденное событие.
     */
    private Event findEventById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие не найден",
                        String.format("Событие с ID %d не найдено", eventId)));
    }

    /**
     * Метод создания предиката с необходимыми для фильтрации событий параметрами.
     * @param users список числовых идентификаторов пользователей-инициаторов;
     * @param states список статусов событий;
     * @param categories список категорий событий;
     * @param rangeStart нижняя граница временного интервала для поиска событий;
     * @param rangeEnd верхняя граница временного интервала для поиска событий.
     * @return предикат с заданными параметрами поиска.
     */
    private BooleanExpression getPredicate(List<Long> users,
                                           List<EventState> states,
                                           List<Long> categories,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd) {
        List<BooleanExpression> predicates = new ArrayList<>();
        QEvent event = QEvent.event;
        if (Objects.nonNull(users) && !users.isEmpty()) {
            predicates.add(event.initiator.id.in(users));
        }
        if (Objects.nonNull(states) && !states.isEmpty()) {
            predicates.add(event.state.in(states));
        }
        if (Objects.nonNull(categories) && !categories.isEmpty()) {
            predicates.add(event.category.id.in(categories));
        }
        if (Objects.nonNull(rangeStart) && Objects.nonNull(rangeEnd)) {
            predicates.add(event.eventDate.after(rangeStart).and(event.eventDate.before(rangeEnd)));
        }
        return predicates.stream().reduce(BooleanExpression::and).orElseThrow();
    }
}