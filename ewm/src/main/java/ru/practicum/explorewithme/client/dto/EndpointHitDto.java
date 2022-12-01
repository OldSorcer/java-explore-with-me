package ru.practicum.explorewithme.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс обращения к эндпоинту, использующийся в качестве тела запроса при обращении к сервису статисики.
 * Свойства: <b>app</b>, <b>uri</b>, <b>ip</b>.
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
}
