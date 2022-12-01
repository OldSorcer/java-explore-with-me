package ru.practicum.explorewithme.stats.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.stats.dto.EndpointHitDto;
import ru.practicum.explorewithme.stats.dto.ViewStatsDto;
import ru.practicum.explorewithme.stats.service.StatsService;

import java.util.List;

/**
 * Класс контроллер описывающий эндпоинты для работы с сервисом статистики.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/")
@Slf4j
public class StatsController {
    private final StatsService service;

    @PostMapping("hit")
    public void save(@RequestBody EndpointHitDto endpointHitDto) {
        service.save(endpointHitDto);
    }

    @GetMapping("stats")
    public List<ViewStatsDto> getViews(@RequestParam String start,
                                       @RequestParam String end,
                                       @RequestParam(defaultValue = "false") Boolean unique,
                                       @RequestParam(required = false) List<String> uris) {
        log.info("Получен запрос к эндпоинту /stats?\n" +
                        "Параметры: start = {}, end = {}, unique = {}, uris = {}",
                start, end, unique, uris);
        List<ViewStatsDto> stats = service.getViews(start, end, unique, uris);
        return stats;
    }
}
