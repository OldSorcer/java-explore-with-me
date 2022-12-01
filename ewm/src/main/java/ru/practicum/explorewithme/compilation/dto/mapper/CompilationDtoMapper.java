package ru.practicum.explorewithme.compilation.dto.mapper;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.event.dto.mapper.EventDtoMapper;
import ru.practicum.explorewithme.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Утилитарный класс позволяющий осуществлять преобразования подборок событий
 * между объектами сущностей и DTO-объектами.
 */
public final class CompilationDtoMapper {
    /**
     * Метод, преобразующий {@link ru.practicum.explorewithme.compilation.dto.NewCompilationDto} в
     * объект класса {@link ru.practicum.explorewithme.compilation.model.Compilation}.
     * @param compilationDto DTО-объект;
     * @param events список событий, который входят в подборку.
     * @return объект класса событий.
     */
    public static Compilation toCompilation(NewCompilationDto compilationDto, List<Event> events) {
        return Compilation.builder()
                .events(events)
                .pinned(compilationDto.isPinned())
                .title(compilationDto.getTitle())
                .build();
    }

    /**
     * Метод, преобразующий объект класса {@link ru.practicum.explorewithme.compilation.model.Compilation} в
     * DTO-объект класса {@link ru.practicum.explorewithme.compilation.dto.CompilationDto},
     * @param compilation объект класса подборки событий.
     * @return DTO-объект события.
     */
    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .events(EventDtoMapper.toEventShortDto(compilation.getEvents()))
                .build();
    }

    /**
     * Метод, преобразуюзий список объектов класса {@link ru.practicum.explorewithme.compilation.model.Compilation} в
     * список DTO-объектов класса {@link ru.practicum.explorewithme.compilation.dto.CompilationDto}.
     * @param compilations список объектов подборок событий.
     * @return список DTO-объектов подборок событий.
     */
    public static List<CompilationDto> toCompilationDto(List<Compilation> compilations) {
        return compilations.stream()
                .map(CompilationDtoMapper::toCompilationDto)
                .collect(Collectors.toList());
    }
}
