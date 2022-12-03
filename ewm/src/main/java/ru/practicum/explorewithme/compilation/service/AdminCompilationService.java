package ru.practicum.explorewithme.compilation.service;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;

/**
 * Интерфейс описывающий основные методы работы с подборками событий.
 * @see ru.practicum.explorewithme.compilation.service.AdminCompilationServiceImpl
 */
public interface AdminCompilationService {
    /**
     * Метод создания подборки событий.
     * @param compilationDto DTO-объект создания новой подборки.
     * @return DTO-объект созданной подборки.
     */
    CompilationDto create(NewCompilationDto compilationDto);

    /**
     * Метод удаления подборки событий по числовому идентификатору.
     * @param compId числовой идентификатор подборки событий.
     */
    void delete(long compId);

    /**
     * Метод удаления события из подборки.
     * @param compId числовой индентификатор подборки событий,
     * @param eventId числовой идентификатор события.
     */
    void deleteEvent(long compId, long eventId);

    /**
     * Метод добавления события в подборку.
     * @param compId числовой идентификатор подборки событий;
     * @param eventId числовой идентификатор события.
     */
    void addEvent(long compId, long eventId);

    /**
     * Метод закрепления подборки событий на главной странице.
     * @param compId числовой идентификатор подборки событий.
     */
    void unpin(long compId);

    /**
     * Метод открепления подборки событий с главной страницы.
     * @param compId числовой идентификатор подборки событий.
     */
    void pin(long compId);
}
