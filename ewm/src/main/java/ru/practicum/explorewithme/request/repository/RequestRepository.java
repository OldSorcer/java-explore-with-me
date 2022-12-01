package ru.practicum.explorewithme.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.request.model.Participation;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс содержащий методы для работы с таблицей базы данных запросов на участие в событии.
 */
public interface RequestRepository extends JpaRepository<Participation, Long> {
    /**
     * Метод поиска всех запросов на участие в событии по числовому идентификатору события.
     *
     * @param eventId числовой идентификатор события.
     * @return список заявок на участие в событии.
     */
    List<Participation> findAllByEventId(long eventId);

    /**
     * Метод поиска всех заявок на участие в событии по числовому идентификатору
     * пользователя оставившего заявку.
     *
     * @param requesterId числовой идентификатор пользователя оставившего заявку на участие в событии.
     * @return список заявок на участие в событии.
     */
    List<Participation> findAllByRequesterId(long requesterId);

    /**
     * Метод поиска заявки на участие в событии по числовому идентификатору пользователя создавшего заявку и
     * числовому идентификатору события.
     *
     * @param requesterId числовой идентификатор пользователя создавшего заявку на участие в событии;
     * @param eventId     числовой идентификатор события.
     * @return объект заявки на участие в событии.
     */
    Optional<Participation> findByRequesterIdAndEventId(long requesterId, long eventId);
}
