package ru.practicum.explorewithme.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Класс DTO предназначенный для работы с телом запроса на создание новой категории.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewCategoryDto {
    @NotNull
    private String name;
}
