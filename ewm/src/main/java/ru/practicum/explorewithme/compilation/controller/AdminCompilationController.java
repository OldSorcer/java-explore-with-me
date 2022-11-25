package ru.practicum.explorewithme.compilation.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.service.AdminCompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/admin/compilations")
@AllArgsConstructor
@Slf4j
@Validated
public class AdminCompilationController {
    private final AdminCompilationService compilationService;

    @PostMapping
    public CompilationDto create(@RequestBody @Valid NewCompilationDto compilationDto) {
        return compilationService.create(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable @Min(1) long compId) {
        compilationService.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable @Min(1) long compId,
                            @PathVariable @Min(1) long eventId) {
        compilationService.deleteEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable @Min(1) long compId,
                         @PathVariable @Min(1) long eventId) {
        compilationService.addEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpin(@PathVariable @Min(1) long compId) {
        compilationService.unpin(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pin(@PathVariable @Min(1) long compId) {
        compilationService.pin(compId);
    }
}
