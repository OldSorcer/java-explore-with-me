package ru.practicum.explorewithme.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.dto.mapper.UserDtoMapper;
import ru.practicum.explorewithme.user.service.AdminUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/users")
@Slf4j
public class AdminUserController {
    private final AdminUserService adminUserService;

    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        log.info("[ewm-service] Получен POST запрос к эндпоинту /admins/users\n" +
                "Тело запроса: {}", userDto);
        return adminUserService.create(UserDtoMapper.toUser(userDto));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        log.info("[ewm-service] Получен DELETE запрос к эндпоинту /admin/users/{}", userId);
        adminUserService.delete(userId);
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту /admin/users\n" +
                        "Параменты запроса: ids = {}, from = {}, size = {}",
                ids, from, size);
        return adminUserService.getByIds(ids, from, size);
    }
}
