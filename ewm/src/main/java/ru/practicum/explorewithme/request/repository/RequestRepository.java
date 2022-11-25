package ru.practicum.explorewithme.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.request.model.Participation;
import ru.practicum.explorewithme.request.model.ParticipationStatus;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Participation, Long> {
    List<Participation> findAllByEventIdAndStatus(long eventId, ParticipationStatus status);

    List<Participation> findAllByEventId(long eventId);

    List<Participation> findAllByRequesterId(long requesterId);

    Optional<Participation> findByRequesterIdAndEventId(long requesterId, long eventId);
}
