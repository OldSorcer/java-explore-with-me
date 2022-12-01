package ru.practicum.explorewithme.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.event.dto.EventShortDto;

import java.util.List;

/**
 * DTO-класс предназначенный для обмена информацией между пользователем и приложением.
 * Свойства: <b>events</b>, <b>id</b>, <b>pinned</b>, <b>title</b>.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CompilationDto {
    private List<EventShortDto> events;
    private long id;
    private boolean pinned;
    private String title;
}
