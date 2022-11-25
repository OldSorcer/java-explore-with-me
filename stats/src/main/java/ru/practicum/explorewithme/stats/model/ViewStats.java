package ru.practicum.explorewithme.stats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ViewStats {
    private String app;
    private String uri;
    private long hits;
}
