package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс DTO для передачи полной информации о событии между клиентом и основным сервисом приложения.
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private long id;
    private UserShortDto initiator;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd - HH:mm:ss")
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private EventState state;
    private String title;
    private long views;
    private List<Comment> comments;
}
