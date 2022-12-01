package ru.practicum.explorewithme.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explorewithme.event.model.Event;

import java.util.List;

/**
 * Интерфейс-репозиторий, предназначенный для работы с таблицей событий базы данных.
 */
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    /**
     * Метод, позволяющий найти событие по числовому идентификатору его инициатора.
     * @param initiatorId числовой идектификатор пользователя-инициатора;
     * @param pageable требуемая для отображения страница.
     * @return список объектов класса события.
     */
    List<Event> findByInitiatorId(long initiatorId, Pageable pageable);

    /**
     * Метод, позволяющий найти событие по его числовому идентификатору и числовому идентификатору
     * пользователя-инициатора события.
     * @param eventId числовой идектификатор события;
     * @param initiatorId числовой идентификатор инициатора.
     * @return объект класса события.
     */
    Event findByIdAndInitiatorId(long eventId, long initiatorId);
}