package ru.practicum.explorewithme.compilation.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.service.PublicCompilationService;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Класс-контроллер описывающий эндпоинты для работы
 * с публичной частью сервиса подборок событий приложения.
 */
@RestController
@RequestMapping("/compilations")
@AllArgsConstructor
@Slf4j
public class PublicCompilationController {
    private final PublicCompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(defaultValue = "false") boolean pinned,
                                       @RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту /compilations/pinned={}&from={}&size={}",
                pinned, from, size);
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable @Min(1) long compId) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту compilations/{}", compId);
        return compilationService.getById(compId);
    }
}
