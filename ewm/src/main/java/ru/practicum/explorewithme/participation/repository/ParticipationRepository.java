package ru.practicum.explorewithme.participation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.participation.model.Participation;
import ru.practicum.explorewithme.participation.model.ParticipationStatus;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findAllByEventIdAndStatus(long eventId, ParticipationStatus status);
    List<Participation> findAllByEventId(long eventId);
    List<Participation> findAllByRequesterId(long requesterId);
}
