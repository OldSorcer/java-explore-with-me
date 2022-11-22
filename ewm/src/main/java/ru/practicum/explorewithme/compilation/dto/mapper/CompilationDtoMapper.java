package ru.practicum.explorewithme.compilation.dto.mapper;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.event.dto.mapper.EventDtoMapper;
import ru.practicum.explorewithme.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

public final class CompilationDtoMapper {
    public static Compilation toCompilation(NewCompilationDto compilationDto, List<Event> events) {
        return Compilation.builder()
                .events(events)
                .pinned(compilationDto.isPinned())
                .title(compilationDto.getTitle())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .events(EventDtoMapper.toEventShortDto(compilation.getEvents()))
                .build();
    }

    public static List<CompilationDto> toCompilationDto(List<Compilation> compilations) {
        return compilations.stream()
                .map(CompilationDtoMapper::toCompilationDto)
                .collect(Collectors.toList());
    }
}
