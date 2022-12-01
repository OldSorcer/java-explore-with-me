package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс DTO содержащий информацию, необходимую для обновления информации об уже существующем событии.
 * Используется при обращении клиента к основному сервису при необходимости изменения события.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateEventDto {
    private String annotation;
    private Long category;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull
    private Long eventId;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}
