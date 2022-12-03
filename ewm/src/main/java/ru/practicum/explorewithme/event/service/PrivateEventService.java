package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventDto;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Интерфейс описывающий основные методы для работы с приватной частью сервиса событий.
 */
public interface PrivateEventService {
    /**
     * Метод создания нового события.
     * @param event DTO-объект создаваемого событий;
     * @param userId числовой идентификатор пользователя-инициатора события.
     * @return DTO-объект созданного события.
     */
    EventFullDto create(NewEventDto event, long userId);

    /**
     * Метод обновления уже существующего события.
     * @param event DTO-объект обновляемого события;
     * @param userId числовой идентификатор пользователя-инициатора события.
     * @return DTO-объект обновленного события.
     */
    EventFullDto update(UpdateEventDto event, long userId);

    /**
     * Метод получения события по числовому идентификтору инициатора события.
     * @param initiatorId числовой идентификатор инициатора события;
     * @param from номер страницы;
     * @param size размер страницы.
     * @return список найденных DTO-объектов события.
     */
    List<EventShortDto> getByInitiatorId(long initiatorId, int from, int size);

    /**
     * Метод получения события по числовому идентификатору события и его инициатора.
     * @param initiatorId числовой идентификатор инициатора события;
     * @param eventId числовой идентификатор события.
     * @return DTO-объект с полной информацией о событии.
     */
    EventFullDto getByInitiatorAndEventId(long initiatorId, long eventId);

    /**
     * Метод, изменяющий статус события на "отмененное" -  {@link ru.practicum.explorewithme.event.model.EventState#CANCELED}.
     * @param initiatorId числовой идентификатор инициатора события;
     * @param eventId числовой идентификатор события.
     * @return DTO-объект с полной информацией о событии.
     */
    EventFullDto cancelEvent(long initiatorId, long eventId);

    /**
     * Метод получения запросов на участие в событии конкретного пользователя.
     * @param initiatorId числовой идентификатор пользователя - инициатора события;
     * @param eventId числовой идентификатор события.
     * @return список DTO-объектов запросов на участие в событии.
     */
    List<ParticipationRequestDto> getRequests(long initiatorId, long eventId);

    /**
     * Метод подтверждения запроса на участие в событии.
     * @param initiatorId числовой идентификатор инициатора события;
     * @param eventId числовой идентификатор события;
     * @param requestId числовой идентификатор запроса на участие в событии.
     * @return DTO-объект с полной информацией о запросе на участие в событии.
     */
    ParticipationRequestDto acceptRequest(long initiatorId, long eventId, long requestId);

    /**
     * Метод отклонения запроса на участие в событии.
     * @param initiatorId числовой идентификатор инициатора события;
     * @param eventId числовой идентификатор события;
     * @param requestId числовой идентификатор запроса на участие в событии.
     * @return DTO-объект с полной информацией о запросе на участие в событии.
     */
    ParticipationRequestDto rejectRequest(long initiatorId, long eventId, long requestId);
}
