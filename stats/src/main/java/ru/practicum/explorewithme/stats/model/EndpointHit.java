package ru.practicum.explorewithme.stats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Класс описывающий обращение к конкретному эндпоинту обладающий свойствами:
 * <b>id</b>, <b>app</b>, <b>uri</b>, <b>ip</b>, <b>timestamp</b>.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stats")
@Builder
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    private long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
