package ru.practicum.explorewithme.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    @NotNull
    @Min(1)
    private long id;
    @NotNull
    private String name;
}
