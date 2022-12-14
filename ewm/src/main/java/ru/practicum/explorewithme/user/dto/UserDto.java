package ru.practicum.explorewithme.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Класс DTO предназначенный для обмена информацие между клиентом и основным сервисом.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDto {
    private long id;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
}
