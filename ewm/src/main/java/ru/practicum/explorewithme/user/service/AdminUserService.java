package ru.practicum.explorewithme.user.service;

import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.model.User;

import java.util.List;

public interface AdminUserService {
    UserDto create(User userDto);
    void delete(long userId);
    List<UserDto> getByIds(List<Long> ids, int from, int size);
}