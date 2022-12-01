package ru.practicum.explorewithme.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс статистики просмотров, использующийся для получения данных от сервиса статистики.
 * Свойства класса: <b>app</b>, <b>uri</b>, <b>hits</b>.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewStatsDto {
    private String app;
    private String uri;
    private long hits;
}
