package ru.practicum.explorewithme.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.client.dto.ViewStatsDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.mapper.EventDtoMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.model.QEvent;
import ru.practicum.explorewithme.event.model.EventSort;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.request.model.ParticipationStatus;
import ru.practicum.explorewithme.request.model.QParticipation;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getAll(String text,
                                      List<Long> categories,
                                      Boolean paid,
                                      LocalDateTime rangeStart,
                                      LocalDateTime rangeEnd,
                                      Boolean onlyAvailable,
                                      EventSort eventSort,
                                      int from,
                                      int size,
                                      HttpServletRequest request) {
        statsClient.save(request);
        BooleanExpression predicate = getPredicate(text, categories, paid, rangeStart, rangeEnd, onlyAvailable);
        if (eventSort.equals(EventSort.EVENT_DATE)) {
            Pageable pageSortedByDate = PageRequest.of(from, size, Sort.by("eventDate").descending());
            return EventDtoMapper.toEventShortDto(eventRepository.findAll(predicate, pageSortedByDate).getContent());
        } else {
            Pageable pageSortedByViews = PageRequest.of(from, size, Sort.by("views").descending());
            return EventDtoMapper.toEventShortDto(eventRepository.findAll(predicate, pageSortedByViews).getContent());
        }
    }

    @Override
    public EventFullDto getById(HttpServletRequest request, long eventId) {
        QEvent event = QEvent.event;
        BooleanExpression predicate = event.id.eq(eventId).and(event.state.eq(EventState.PUBLISHED));
        Event foundedEvent = eventRepository.findOne(predicate)
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено",
                String.format("События с ID %d не найдено", eventId)));
        List<ViewStatsDto> responseBody = statsClient.getViews(Collections.singletonList(foundedEvent), true);
        if (!responseBody.isEmpty()) {
            foundedEvent.setViews(responseBody.get(0).getHits());
        }
        statsClient.save(request);
        return EventDtoMapper.toEventFullDto(foundedEvent);
    }

    /**
     * Метод получения предиката на основании требуемых параметров поиска событий.
     * @param text текст или фрагмент текста в аннотации или описании события;
     * @param categories чиловой идентификатор категорий событий;
     * @param paid параметр определяющий требуется ли оплата за участие в событии;
     * @param rangeStart нижняя граница временного интервала для поиска событий;
     * @param rangeEnd верхняя граница временного интервала для поиска событий;
     * @param onlyAvailable только доступные события;
     * @return предикат с заданными параметрами.
     */
    private BooleanExpression getPredicate(String text,
                                           List<Long> categories,
                                           Boolean paid,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Boolean onlyAvailable) {
        List<BooleanExpression> predicates = new ArrayList<>();
        QEvent event = QEvent.event;
        QParticipation participation = QParticipation.participation;
        predicates.add(event.state.eq(EventState.PUBLISHED));
        if (Objects.nonNull(text)) {
            predicates.add(event.annotation.containsIgnoreCase(text).or(event.description.containsIgnoreCase(text)));
        }
        if (Objects.nonNull(categories) && !categories.isEmpty()) {
            predicates.add(event.category.id.in(categories));
        }
        if (Objects.nonNull(paid)) {
            predicates.add(event.paid.eq(paid));
        }
        if (Objects.nonNull(rangeStart) && Objects.nonNull(rangeEnd)) {
            predicates.add(event.eventDate.after(rangeStart).and(event.eventDate.before(rangeEnd)));
        } else {
            predicates.add(event.eventDate.after(LocalDateTime.now()));
        }
        if (Objects.nonNull(onlyAvailable) && onlyAvailable) {
            predicates.add(event.participantLimit.eq(0)
                    .or(event.requestModeration.isFalse().and(event.participantLimit.goe(participation.count())))
                    .or(event.requestModeration.isTrue().and(event.participantLimit.goe(participation.status.eq(ParticipationStatus.CONFIRMED).count()))));
        }
        return predicates.stream().reduce(BooleanExpression::and).get();
    }
}