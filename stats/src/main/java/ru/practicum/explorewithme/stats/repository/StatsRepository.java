package ru.practicum.explorewithme.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.stats.model.EndpointHit;
import ru.practicum.explorewithme.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.explorewithme.stats.model.ViewStats(e.app, e.uri, COUNT(e.id)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri "
    )
    List<ViewStats> findAll(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.explorewithme.stats.model.ViewStats(e.app, e.uri, COUNT(e.id))" +
            "FROM EndpointHit AS e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri, e.ip")
    List<ViewStats> findAllUnique(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.explorewithme.stats.model.ViewStats(e.app, e.uri, count(e.id)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.uri IN :uris AND e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri "
    )
    List<ViewStats> findAllByUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.explorewithme.stats.model.ViewStats(e.app, e.uri, count(e.id)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.uri IN :uris AND e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri, e.ip "
    )
    List<ViewStats> findAllByUrisUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}
