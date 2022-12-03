package ru.practicum.explorewithme.category.dto;

import ru.practicum.explorewithme.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Утилитарный класс преобразующий DTO-объекты
 * в объекты класса {@link ru.practicum.explorewithme.category.model.Category}
 */
public class CategoryDtoMapper {
    /**
     * Метод преобразования объекта категории в DTO-объект.
     * @param category объект категории.
     * @return DTO-объект.
     */
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    /**
     * Метод преобразования списка объектов категории в список DTO-объектов.
     * @param categoryList список объектов категории.
     * @return список DTO-объектов.
     */
    public static List<CategoryDto> toCategoryDto(List<Category> categoryList) {
        return categoryList.stream().map(CategoryDtoMapper::toCategoryDto).collect(Collectors.toList());
    }

    /**
     * Метод преобразования DTO-объекта категории для создания в объект класса категории.
     * @param newCategoryDto DTO-объект для создания категории.
     * @return объект категории.
     */
    public static Category toCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }

    /**
     * Метод преобразующий DTO-объект категории в объект класса категории.
     * @param categoryDto DTO-объект категории.
     * @return объект класса категории.
     */
    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
