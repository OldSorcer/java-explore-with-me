package ru.practicum.explorewithme.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.compilation.model.Compilation;

import java.util.List;

/**
 * Интерфейс репозитория предназначенный для работы с базой данных подборок событий.
 */
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    /**
     * Метод, позволяющий получить список закрепленных/незакрепленных подборок событий.
     * @param pinned указывает на статус подборки событий;
     * @param page характеристика страницы подборок событий.
     * @return список объектов подборок событий.
     */
    List<Compilation> findAllByPinned(boolean pinned, Pageable page);
}
