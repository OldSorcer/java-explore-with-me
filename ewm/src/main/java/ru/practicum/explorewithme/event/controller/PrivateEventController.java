package ru.practicum.explorewithme.event.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventDto;
import ru.practicum.explorewithme.event.service.PrivateEventService;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Класс-контроллер, описывающий эндпоинты для работы с приватной частью сервиса событий.
 */
@RestController
@RequestMapping("/users/{userId}/events")
@AllArgsConstructor
@Slf4j
@Validated
public class PrivateEventController {
    private final PrivateEventService eventService;

    @PostMapping
    public EventFullDto create(@RequestBody @Valid NewEventDto eventDto,
                               @PathVariable @Min(1) long userId) {
        log.info("[ewm-service] Поступил POST запрос к эндпоинту /users/{}/events", userId);
        return eventService.create(eventDto, userId);
    }

    @PatchMapping
    public EventFullDto update(@RequestBody @Valid UpdateEventDto eventDto,
                               @PathVariable @Min(1) long userId) {
        log.info("[ewm-service] Получен PATCH запрос к эндпоинту /users/{}/events", userId);
        return eventService.update(eventDto, userId);
    }

    @GetMapping
    public List<EventShortDto> getByInitiatorId(@PathVariable @Min(1) long userId,
                                                @RequestParam(required = false, defaultValue = "0") int from,
                                                @RequestParam(required = false, defaultValue = "10") int size) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту /users/{}/events/?from={}&size={}",
                userId, from, size);
        return eventService.getByInitiatorId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getByEventId(@PathVariable @Min(1) long userId,
                                     @PathVariable @Min(1) long eventId) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту /users/{}/events/{}",
                userId, eventId);
        return eventService.getByInitiatorAndEventId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable @Min(1) long userId,
                                    @PathVariable @Min(1) long eventId) {
        log.info("[ewm-service] Получен PATCH запрос к эндпоинту /users/{}/events/{}",
                userId, eventId);
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByEventId(@PathVariable @Min(1) long userId,
                                                              @PathVariable @Min(1) long eventId) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту users/{}/events/{}/requests",
                userId, eventId);
        return eventService.getRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable @Min(1) long userId,
                                                  @PathVariable @Min(1) long eventId,
                                                  @PathVariable @Min(1) long reqId) {
        log.info("[ewm-service] Получен PATCH запрос к эндпоинту users/{}/events/{}/requests/{}/confirm",
                userId, eventId, reqId);
        return eventService.acceptRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable @Min(1) long userId,
                                                 @PathVariable @Min(1) long eventId,
                                                 @PathVariable @Min(1) long reqId) {
        log.info("[ewm-service] Получен PATCH запрос к эндпоинту users/{}/events/{}/requests/{}/reject",
                userId, eventId, reqId);
        return eventService.rejectRequest(userId, eventId, reqId);
    }
}
