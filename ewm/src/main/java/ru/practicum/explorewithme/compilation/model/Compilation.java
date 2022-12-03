package ru.practicum.explorewithme.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.event.model.Event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Класс подборки событий обладабщий свойствами:
 * <b>id</b>, <b>pinned</b>, <b>title</b>.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "compilations")
@Builder
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private long id;
    @Column(nullable = false)
    private boolean pinned;
    @Column(nullable = false)
    private String title;
    @ManyToMany
    @JoinTable(name = "events_compilations",
    joinColumns = @JoinColumn(name = "compilation_id"),
    inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
}
