package ru.practicum.explorewithme.event.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.UpdateEventDto;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.service.AdminEventService;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@AllArgsConstructor
@Validated
@Slf4j
public class AdminEventController {
    private final AdminEventService eventService;

    @GetMapping
    public List<EventFullDto> getAll(@RequestParam(required = false) List<Long> users,
                                     @RequestParam(required = false) List<EventState> states,
                                     @RequestParam(required = false) List<Long> categories,
                                     @RequestParam(required = false)
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                     @RequestParam(required = false)
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                     @RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту /admin/events\n" +
                "Параметры: users ={}, states = {}, categories = {}, rangeStart = {}, rangeEnd = {}, from = {}" +
                "size = {}", users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.getAll(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto update(@RequestBody UpdateEventDto updateEventDto,
                               @PathVariable @Min(1) long eventId) {
        log.info("[ewm-service]Получен PUT запрос к эндпоинту /admin/events/{}", eventId);
        return eventService.update(updateEventDto, eventId);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publish(@PathVariable @Min(1) long eventId) {
        log.info("[ewm-service] Получен PATCH запрос к эндпоинту /admin/{}/publish", eventId);
        return eventService.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto reject(@PathVariable @Min(1) long eventId) {
        log.info("[ewm-service] Получен PATCH запрос к эндпоинту /admin/{}/reject", eventId);
        return eventService.reject(eventId);
    }
}
