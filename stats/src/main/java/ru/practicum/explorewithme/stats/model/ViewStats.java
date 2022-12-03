package ru.practicum.explorewithme.stats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс описывающий статистику обращений к конкретному эндпоинту.
 */
@AllArgsConstructor
@Getter
@Setter
public class ViewStats {
    private String app;
    private String uri;
    private long hits;
}
