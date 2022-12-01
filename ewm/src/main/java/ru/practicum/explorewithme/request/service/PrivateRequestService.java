package ru.practicum.explorewithme.request.service;

import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Интерфейс взаимодействия с таблицей базы данных заявок на участие в событии.
 */
public interface PrivateRequestService {
    /**
     * Метод создания новой заявки на участие в событии.
     * @param userId числовой идентификатор пользователя создающего заявку на участие в событии;
     * @param eventId числовой идентификатор события.
     * @return DTO-объект заявки на участие в событии.
     */
    ParticipationRequestDto create(long userId, long eventId);

    /**
     * Метод получения всех заявок на участие в событии по числовому идентификатору пользователя создавшего
     * заявку на участие в событии.
     * @param userId числовой идентификатор пользователя, создавшего заявку на участие в событии.
     * @return список DTO-объектов заявок на участие в событии.
     */
    List<ParticipationRequestDto> getAll(long userId);

    /**
     * Метод отмены заявки на участие в событии.
     * @param userId числовой идентификатор пользователя, создавшего заявку на участие в событии;
     * @param requestId числовой идентификатор заявки на участие в событии.
     * @return DTO-объект заявки на участие в событии.
     */
    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
