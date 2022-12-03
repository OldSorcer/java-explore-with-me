package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.model.Category;

import java.util.List;

/**
 * Интерфейс описывающий логику работы с <b>категориями</b> событий.
 */
public interface CategoryService {
    /**
     * Метод сохранения категории события.
     * @param category объект категории для сохранения.
     * @return сохраненный в базе данных объект категории.
     */
    Category save(Category category);

    /**
     * Метод удаление категории события.
     * @param categoryId числовой идентификатор категории события.
     */
    void delete(long categoryId);

    /**
     * Метод получения списка всех категорий, сохраенных в базе данных постранично.
     * @param from номер первой страницы;
     * @param size количество категорий в странице.
     * @return список найденных категорий.
     */
    List<Category> getAll(int from, int size);

    /**
     * Метод получения категории события по числовому идентификатору.
     * @param categoryId числовой идентификатор категории.
     * @return найденный объект категории.
     */
    Category getById(long categoryId);
}
