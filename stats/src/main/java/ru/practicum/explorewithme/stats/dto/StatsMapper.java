package ru.practicum.explorewithme.stats.dto;

import ru.practicum.explorewithme.stats.model.EndpointHit;
import ru.practicum.explorewithme.stats.model.ViewStats;

import java.time.LocalDateTime;

/**
 * Утилитарный класс предназначенный для преобразования объектов класса
 * {@link ru.practicum.explorewithme.stats.dto.EndpointHitDto} в
 * объекты класса {@link ru.practicum.explorewithme.stats.model.EndpointHit},
 * а так же объектов класса {@link ru.practicum.explorewithme.stats.model.ViewStats} в
 * объекты класса {@link ru.practicum.explorewithme.stats.dto.ViewStatsDto}.
 */
public final class StatsMapper {
    /**
     * Метод преобразуюзий объект класса {@link ru.practicum.explorewithme.stats.dto.EndpointHitDto} в
     * объект класса {@link ru.practicum.explorewithme.stats.model.EndpointHit}.
     * @param endpointHitDto объект класса {@link ru.practicum.explorewithme.stats.dto.EndpointHitDto}.
     * @return объект класса {@link ru.practicum.explorewithme.stats.model.EndpointHit}.
     */
    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .app(endpointHitDto.getApp())
                .ip(endpointHitDto.getIp())
                .uri(endpointHitDto.getUri())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Метод преобразующий объекты класса {@link ru.practicum.explorewithme.stats.model.ViewStats} в
     * объект класса {@link ru.practicum.explorewithme.stats.dto.ViewStatsDto}.
     * @param viewStats объект класса {@link ru.practicum.explorewithme.stats.model.ViewStats}.
     * @return объект класса {@link ru.practicum.explorewithme.stats.dto.ViewStatsDto}.
     */
    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return  ViewStatsDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits())
                .build();
    }
}