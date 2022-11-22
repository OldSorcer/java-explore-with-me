package ru.practicum.explorewithme.stats.service;

import net.bytebuddy.asm.Advice;
import ru.practicum.explorewithme.stats.dto.EndpointHitDto;
import ru.practicum.explorewithme.stats.dto.ViewStatsDto;
import ru.practicum.explorewithme.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void save(EndpointHitDto endpointHitDto);
    List<ViewStatsDto> getViews(String start,
                                String end,
                                Boolean unique,
                                List<String> uris);
}
