package ru.practicum.explorewithme.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO-класс предназначенный для работы с телом запроса на создание новой <b>подброки</b> событий.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewCompilationDto {
    private List<Long> events;
    private boolean pinned;
    @NotNull
    private String title;
}
