package ru.practicum.explorewithme.compilation.service;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;

public interface AdminCompilationService {
    CompilationDto create(NewCompilationDto compilationDto);

    void delete(long compId);

    void deleteEvent(long compId, long eventId);

    void addEvent(long compId, long eventId);

    void unpin(long compId);

    void pin(long compId);
}
