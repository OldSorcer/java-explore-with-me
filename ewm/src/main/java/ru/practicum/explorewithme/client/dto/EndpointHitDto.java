package ru.practicum.explorewithme.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
}
