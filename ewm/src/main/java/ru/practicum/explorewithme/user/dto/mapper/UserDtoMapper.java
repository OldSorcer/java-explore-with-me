package ru.practicum.explorewithme.user.dto.mapper;

import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.dto.UserShortDto;
import ru.practicum.explorewithme.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Утилитарный класс для преобразования объектов класса {@link ru.practicum.explorewithme.user.model.User} в
 * объекты класса {@link ru.practicum.explorewithme.user.dto.UserDto} и
 * {@link ru.practicum.explorewithme.user.dto.UserShortDto}.
 * <br> Поддерживает обратное преобразование.
 */
public final class UserDtoMapper {
    /**
     * Метод преобразования объекта класса {@link ru.practicum.explorewithme.user.dto.UserDto} в
     * объект класса {@link ru.practicum.explorewithme.user.model.User}.
     * @param userDto объект класса {@link ru.practicum.explorewithme.user.dto.UserDto}.
     * @return объект класса {@link ru.practicum.explorewithme.user.model.User}.
     */
    public static User toUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }

    /**
     * Метод преобразующий объект класса {@link ru.practicum.explorewithme.user.model.User} в
     * объект класса {@link ru.practicum.explorewithme.user.dto.UserDto}.
     * @param user объект класса {@link ru.practicum.explorewithme.user.model.User}.
     * @return объект класса {@link ru.practicum.explorewithme.user.dto.UserDto}.
     */
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    /**
     * Метод преобразования списка объектов класса {@link ru.practicum.explorewithme.user.model.User} в
     * список объектов класса {@link ru.practicum.explorewithme.user.dto.UserDto}.
     * @param users список объектов класса {@link ru.practicum.explorewithme.user.model.User}.
     * @return список объектов класса {@link ru.practicum.explorewithme.user.dto.UserDto}.
     */
    public static List<UserDto> toUserDto(List<User> users) {
        return users.stream().map(UserDtoMapper::toUserDto).collect(Collectors.toList());
    }

    /**
     * Метод преобразования объекта класса {@link ru.practicum.explorewithme.user.model.User} в
     * объект класса {@link ru.practicum.explorewithme.user.dto.UserShortDto}.
     * @param user объект класса {@link ru.practicum.explorewithme.user.model.User}.
     * @return объект класса {@link ru.practicum.explorewithme.user.dto.UserShortDto}.
     */
    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}