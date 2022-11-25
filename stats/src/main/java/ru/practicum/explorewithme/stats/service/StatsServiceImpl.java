package ru.practicum.explorewithme.stats.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.stats.dto.EndpointHitDto;
import ru.practicum.explorewithme.stats.dto.StatsMapper;
import ru.practicum.explorewithme.stats.dto.ViewStatsDto;
import ru.practicum.explorewithme.stats.model.ViewStats;
import ru.practicum.explorewithme.stats.repository.StatsRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public void save(EndpointHitDto endpointHitDto) {
        statsRepository.save(StatsMapper.toEndpointHit(endpointHitDto));
    }

    @Override
    public List<ViewStatsDto> getViews(String start, String end, Boolean unique, List<String> uris) {
        List<ViewStats> stats;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime decodedStartTime = LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8), formatter);
        LocalDateTime decodedEndTime = LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8), formatter);
        if (Objects.isNull(uris) || uris.isEmpty()) {
            if (unique) {
                stats = statsRepository.findAllUnique(decodedStartTime, decodedEndTime);
            } else {
                stats = statsRepository.findAll(decodedStartTime, decodedEndTime);
            }
        } else {
            if (unique) {
                stats = statsRepository.findAllByUrisUnique(decodedStartTime, decodedEndTime, uris);
            } else {
                stats = statsRepository.findAllByUris(decodedStartTime, decodedEndTime, uris);
            }
        }
        return stats.stream()
                .map(StatsMapper::toViewStatsDto)
                .collect(Collectors.toList());
    }
}
