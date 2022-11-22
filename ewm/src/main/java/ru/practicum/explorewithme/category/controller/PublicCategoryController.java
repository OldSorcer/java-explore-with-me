package ru.practicum.explorewithme.category.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryDtoMapper;
import ru.practicum.explorewithme.category.service.CategoryService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@Validated
@Slf4j
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту /categories\n" +
                "Параметры запроса: from = {}, size = {}",
                from, size);
        return CategoryDtoMapper.toCategoryDto(categoryService.getAll(from, size));
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getById(@PathVariable @Min(1) long categoryId) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту /categories/{}\n", categoryId);
        return CategoryDtoMapper.toCategoryDto(categoryService.getById(categoryId));
    }
}
