package ru.practicum.explorewithme.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Класс события обладающий свойствами:
 * <b>id</b>, <b>annotation</b>, <b>category</b>, <b>confirmedRequests</b>,
 * <b>createdOn</b>, <b>description</b>, <b>eventDate</b>, <b>initiator</b>,
 * <b>lat</b>, <b>lon</b>, <b>paid</b>, <b>participantLimit</b>, <b>publishedOn</b>,
 * <b>requestModeration</b>, <b>state</b>, <b>title</b>, <b>views</b>.
 * <br> Свойству <b>category</b> соответствует объект класса {@link ru.practicum.explorewithme.category.model.Category};
 * <br> Свойству <b>initiator</b> - объект класса {@link ru.practicum.explorewithme.user.model.User};
 * <br> Свойству <b>state</b> - перечисление {@link ru.practicum.explorewithme.event.model.EventState}
 * @see ru.practicum.explorewithme.user.model.User
 * @see ru.practicum.explorewithme.category.model.Category
 * @see ru.practicum.explorewithme.event.model.EventState
 */
@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private long id;
    @Column(nullable = false)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    private int confirmedRequests;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    private String description;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    private double lat;
    private double lon;
    @Column(nullable = false)
    private boolean paid;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private boolean requestModeration;
    @Enumerated(value = EnumType.STRING)
    private EventState state;
    @Column(nullable = false)
    private String title;
    private long views;
}