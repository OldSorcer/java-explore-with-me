package ru.practicum.explorewithme.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.event.service.PrivateEventService;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.request.service.PrivateRequestService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Validated
public class PrivateRequestController {
    private final PrivateRequestService privateRequestService;
    private final PrivateEventService eventService;

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto create(@PathVariable @Min(1) long userId,
                                          @RequestParam long eventId) {
        return privateRequestService.create(userId, eventId);
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getByRequesterId(@PathVariable @Min(1) long userId) {
        return privateRequestService.getAll(userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelSelfRequest(@PathVariable @Min(1) long userId,
                                                     @PathVariable @Min(1) long requestId) {
        return privateRequestService.cancelRequest(userId, requestId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getByEventId(@PathVariable @Min(1) long userId,
                                                      @PathVariable @Min(1) long eventId) {
        return eventService.getRequests(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable @Min(1) long userId,
                                                  @PathVariable @Min(1) long eventId,
                                                  @PathVariable @Min(1) long reqId) {
        return eventService.acceptRequest(userId, eventId, reqId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable @Min(1) long userId,
                                                 @PathVariable @Min(1) long eventId,
                                                 @PathVariable @Min(1) long reqId) {
        return eventService.rejectRequest(userId, eventId, reqId);
    }
}
