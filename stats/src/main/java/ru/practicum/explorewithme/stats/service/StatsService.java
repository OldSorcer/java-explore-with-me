package ru.practicum.explorewithme.stats.service;

import ru.practicum.explorewithme.stats.dto.EndpointHitDto;
import ru.practicum.explorewithme.stats.dto.ViewStatsDto;

import java.util.List;

/**
 * Интерфейс описывающий основные методы работы с сервисом статистики.
 * @see ru.practicum.explorewithme.stats.service.StatsServiceImpl
 */
public interface StatsService {
    /**
     * Метод сохранения статистики обращения к конкретному эндпоинту.
     * @param endpointHitDto - объект класса {@link ru.practicum.explorewithme.stats.dto.EndpointHitDto}.
     */
    void save(EndpointHitDto endpointHitDto);

    /**
     * Метод получения статистики просмотров по заданным параметрам.
     * @param start начало временного интервала для получения статистики;
     * @param end конец временного интервала для получения статистики;
     * @param unique получение статистики уникальных запросов;
     * @param uris список эндпоинтов, для которых необходимо получить статистику.
     * @return список объектов класса {@link ru.practicum.explorewithme.stats.dto.ViewStatsDto}.
     */
    List<ViewStatsDto> getViews(String start,
                                String end,
                                Boolean unique,
                                List<String> uris);
}
