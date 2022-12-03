package ru.practicum.explorewithme.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.stats.model.EndpointHit;
import ru.practicum.explorewithme.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс содержащий методы для работы с таблицей базы данных сервиса статистики.
 */
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    /**
     * Метод получения общей статистики за определенный временной интервал.
     * @param start время начала временного интервала;
     * @param end время окончания временного интервала.
     * @return список объектов статистики обращения к эндпоинтам.
     */
    @Query("SELECT new ru.practicum.explorewithme.stats.model.ViewStats(e.app, e.uri, COUNT(e.id)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri "
    )
    List<ViewStats> findAll(LocalDateTime start, LocalDateTime end);

    /**
     * Метод получения общей статистики с уникальным значением поля <b>ip</b> за определенный
     * временной интервал.
     * @param start время начала временного интервала;
     * @param end время окончания временного интервала.
     * @return список объектов статистики обращения к эндпоинтам.
     */
    @Query("SELECT new ru.practicum.explorewithme.stats.model.ViewStats(e.app, e.uri, COUNT(e.id))" +
            "FROM EndpointHit AS e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri, e.ip")
    List<ViewStats> findAllUnique(LocalDateTime start, LocalDateTime end);

    /**
     * Метод получения статистики обращения к конкретным эндпоинтам за определенный временной интервал.
     * @param start время начала временного интервала;
     * @param end время окончания временного интервала;
     * @param uris список эндпоинтов.
     * @return список объектов статистики обращения к эндпоинтам.
     */
    @Query("SELECT new ru.practicum.explorewithme.stats.model.ViewStats(e.app, e.uri, count(e.id)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.uri IN :uris AND e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri "
    )
    List<ViewStats> findAllByUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    /**
     * Метод получения статистики обращения к конерктным эндпоинтам за определенный интервал с
     * уникальным значением поля <b>ip</b>.
     * @param start время начала временного интервала;
     * @param end время окончания временного интервала;
     * @param uris список эндпоинтов.
     * @return список объектов класса статистики обращения к эндпоинтам.
     */
    @Query("SELECT new ru.practicum.explorewithme.stats.model.ViewStats(e.app, e.uri, count(e.id)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.uri IN :uris AND e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri, e.ip "
    )
    List<ViewStats> findAllByUrisUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}
