package ru.practicum.explorewithme.compilation.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.dto.mapper.CompilationDtoMapper;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.List;

/**
 * Класс описывающий логику работы с административной частью сервиса подборки событий.
 * @see ru.practicum.explorewithme.compilation.service.AdminCompilationService
 */
@Service
@AllArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto create(NewCompilationDto compilationDto) {
        List<Event> foundedEvents = eventRepository.findAllById(compilationDto.getEvents());
        Compilation createdCompilation = CompilationDtoMapper.toCompilation(compilationDto, foundedEvents);
        return CompilationDtoMapper.toCompilationDto(compilationRepository.save(createdCompilation));
    }

    @Override
    public void delete(long compId) {
        try {
            compilationRepository.deleteById(compId);
        } catch (EmptyResultDataAccessException exc) {
            throw new EntityNotFoundException("Подборка не найдена",
                    String.format("Подборка с ID %d не найдена", compId));
        }
    }

    @Override
    public void deleteEvent(long compId, long eventId) {
        Compilation foundedCompilation = findCompilation(compId);
        Event foundedEvent = findEventInCompilation(eventId, foundedCompilation);
        foundedCompilation.getEvents().remove(foundedEvent);
        compilationRepository.save(foundedCompilation);
    }

    @Override
    public void addEvent(long compId, long eventId) {
        Compilation foundedCompilation = findCompilation(compId);
        Event foundedEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено",
                        String.format("Событие с ID %d не найдено", eventId)));
        foundedCompilation.getEvents().add(foundedEvent);
        compilationRepository.save(foundedCompilation);
    }

    @Override
    public void unpin(long compId) {
        Compilation foundedCompilation = findCompilation(compId);
        foundedCompilation.setPinned(false);
        compilationRepository.save(foundedCompilation);
    }

    @Override
    public void pin(long compId) {
        Compilation foundedCompilation = findCompilation(compId);
        if (foundedCompilation.isPinned()) {
            throw new ConflictException("Запрещенная операция", "Компиляция уже закреплена");
        }
        foundedCompilation.setPinned(true);
        compilationRepository.save(foundedCompilation);
    }

    /**
     * Метод поиска подборки событий в репозитории по числовому идентификатору.
     * @param compId числовой идентификатор подборки событий.
     * @return объект подборки событий.
     */
    private Compilation findCompilation(long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена",
                        String.format("Подборка с ID %d не найдена", compId)));
    }

    /**
     * Метод поиска события в подборки событий по числовому идентификатору.
     * @param eventId числовой идентификатор события;
     * @param foundedCompilation подборка событий, в которой необходимо найти событие.
     * @return объект класса события.
     */
    private Event findEventInCompilation(long eventId, Compilation foundedCompilation) {
        return foundedCompilation.getEvents().stream()
                .filter(e -> e.getId() == eventId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено",
                        String.format("Событие с ID %d не состоит в подборке")));
    }
}
