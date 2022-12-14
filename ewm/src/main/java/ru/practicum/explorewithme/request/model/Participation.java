package ru.practicum.explorewithme.request.model;

import lombok.*;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс описывающий запрос на участие в событии обладающий
 * свойствами: <b>id</b>, <b>created</b>, <b>requester</b>, <b>event</b>,
 * <b>status</b>,
 * где <b>requester</b> - объект класса {@link ru.practicum.explorewithme.user.model.User},
 * <b>event</b> - объект класса {@link ru.practicum.explorewithme.event.model.Event}.
 */
@Entity
@Table(name = "participations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private long id;
    @Column(nullable = false)
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @Enumerated(value = EnumType.STRING)
    private ParticipationStatus status;
}
