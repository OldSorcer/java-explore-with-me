package ru.practicum.explorewithme.stats.service;

import ru.practicum.explorewithme.stats.dto.EndpointHitDto;
import ru.practicum.explorewithme.stats.dto.ViewStatsDto;

import java.util.List;

public interface StatsService {
    void save(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getViews(String start,
                                String end,
                                Boolean unique,
                                List<String> uris);
}
