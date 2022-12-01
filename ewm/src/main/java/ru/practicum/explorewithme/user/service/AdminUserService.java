package ru.practicum.explorewithme.user.service;

import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.model.User;

import java.util.List;

/**
 * Интерфейс описывающий основные методы сервиса работы с пользователями.
 */
public interface AdminUserService {
    /**
     * Метод создания нового пользователя.
     * @param userDto DTO-объект класса {@link ru.practicum.explorewithme.user.dto.UserDto}.
     * @return DTO-объект созданного пользователя.
     */
    UserDto create(User userDto);

    /**
     * Метод удаления пользователя по идентификатору.
     * @param userId числовой идентификатор пользователя.
     */
    void delete(long userId);

    /**
     * Метож получения всех пользователей постранично.
     * @param ids список числовых идентификаторов пользователей;
     * @param from порядковый номер первой страницы;
     * @param size количество пользователей на одной странице.
     * @return список DTO-объектов пользователей.
     */
    List<UserDto> getByIds(List<Long> ids, int from, int size);
}