package ru.practicum.explorewithme.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс DTO содержащий краткую информацию о пользователе
 * предназначенный для обмена информацией между клиентом и основным сервисом приложения.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserShortDto {
    private long id;
    private String name;
}
