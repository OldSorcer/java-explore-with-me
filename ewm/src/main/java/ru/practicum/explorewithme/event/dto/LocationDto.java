package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс DTO <b>локации</b>, в которой должно произойти событие.
 * Используется для обмена информацией между клиентом и основным сервисом приложения.
 */
@AllArgsConstructor
@Getter
@Setter
public class LocationDto {
    private double lat;
    private double lon;
}
