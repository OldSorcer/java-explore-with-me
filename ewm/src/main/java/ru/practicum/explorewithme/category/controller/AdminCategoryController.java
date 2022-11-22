package ru.practicum.explorewithme.category.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryDtoMapper;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/admin/categories")
@AllArgsConstructor
@Validated
@Slf4j
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto add(@Valid @RequestBody NewCategoryDto categoryDto) {
        log.info("[ewm-service] Получен POST запрос к эндпоинту /admin/categories\n" +
                "Добавление категории: {}", categoryDto);
        Category newCategory = categoryService.save(CategoryDtoMapper.toCategory(categoryDto));
        return CategoryDtoMapper.toCategoryDto(newCategory);
    }

    @PatchMapping
    public CategoryDto update(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("[ewm-service] Получен PATCH запрос к эндпоинту /admin/categories\n" +
                "Обновление категории: {}", categoryDto);
        Category updatedCategory = categoryService.save(CategoryDtoMapper.toCategory(categoryDto));
        return CategoryDtoMapper.toCategoryDto(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public void delete(@PathVariable @Min(1) long categoryId) {
        log.info("[ewm-service] Получен DELETE запрос к эндпоинту /admin/categories/{}", categoryId);
        categoryService.delete(categoryId);
    }
}
