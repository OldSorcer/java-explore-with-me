package ru.practicum.explorewithme.category.dto;

import ru.practicum.explorewithme.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryDtoMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static List<CategoryDto> toCategoryDto(List<Category> categoryList) {
        return categoryList.stream().map(CategoryDtoMapper::toCategoryDto).collect(Collectors.toList());
    }

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
