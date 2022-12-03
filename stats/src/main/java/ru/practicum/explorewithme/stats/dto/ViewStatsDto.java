package ru.practicum.explorewithme.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс DTO предназначенный для обмена информацией между клиентов и сервисом статистики.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ViewStatsDto {
    private String app;
    private String uri;
    private long hits;
}
