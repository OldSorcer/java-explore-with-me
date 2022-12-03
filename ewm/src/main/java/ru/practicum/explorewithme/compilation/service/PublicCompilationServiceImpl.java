package ru.practicum.explorewithme.compilation.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.mapper.CompilationDtoMapper;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.List;

/**
 * Класс описывающий логику работы сервиса подборки событий.
 * @see ru.practicum.explorewithme.compilation.service.PublicCompilationService
 */
@Service
@AllArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> getAll(boolean pinned, int from, int size) {
        Pageable page = PageRequest.of(from, size, Sort.by("Id"));
        return CompilationDtoMapper.toCompilationDto(compilationRepository.findAllByPinned(pinned, page));
    }

    @Override
    public CompilationDto getById(long compId) {
        Compilation foundedCompilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подброка не найдена",
                        String.format("Событие с ID %d не найден",compId)));
        return CompilationDtoMapper.toCompilationDto(foundedCompilation);
    }
}
