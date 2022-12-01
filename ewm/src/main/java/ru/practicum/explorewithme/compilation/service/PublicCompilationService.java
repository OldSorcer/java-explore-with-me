package ru.practicum.explorewithme.compilation.service;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;

import java.util.List;

/**
 * Интерфейс описывающий основные методы работы с публичной частью сервиса подборки событий.
 * @see ru.practicum.explorewithme.compilation.service.PublicCompilationServiceImpl
 */
public interface PublicCompilationService {
    /**
     * Метод получения списка подборок событий.
     * @param pinned статус закрепления подборки событий на главной странице;
     * @param from числовой идентификатор первой страницы;
     * @param size количество подборок событий на одной странице.
     * @return список DTO-объектов подборок событий.
     */
    List<CompilationDto> getAll(boolean pinned, int from, int size);

    /**
     * Метод получения подборки событий по числовому идентификатору.
     * @param compId числовой идентификатор подборки событий.
     * @return DTO-объект подборки событий.
     */
    CompilationDto getById(long compId);
}
