package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.model.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);
    void delete(long categoryId);
    List<Category> getAll(int from, int size);
    Category getById(long categoryId);
}
