package ru.practicum.explorewithme.category.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException exc) {
            throw new ConflictException("Возник конфликт при сохранении китегории",
                    String.format("Категория \"%s\" уже существует", category.getName()));
        }
    }

    @Override
    public void delete(long categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
        } catch (EmptyResultDataAccessException exc) {
            throw new EntityNotFoundException("Категория не найдена",
                    String.format("Категории с ID %d не существует", categoryId));
        }
    }

    @Override
    public List<Category> getAll(int from, int size) {
        PageRequest page = PageRequest.of(from, size);
        return categoryRepository.findAll(page).getContent();
    }

    @Override
    public Category getById(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Категория не найдена",
                        String.format("Категории с ID %d не существует", categoryId)));
    }
}