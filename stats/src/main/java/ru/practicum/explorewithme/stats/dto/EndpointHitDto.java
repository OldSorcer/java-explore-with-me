package ru.practicum.explorewithme.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Класс DTO содержищий информацию об обращении к конкретному эндпоинту.
 * <br> Свойства: <b>id</b>, <b>app</b>, <b>uri</b>, <b>ip</b>, <b>timestamp</b>.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EndpointHitDto {
    private long id;
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
