package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.UpdateEventDto;
import ru.practicum.explorewithme.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса, описывающий основные методы для работы сервиса событий.
 */
public interface AdminEventService {
    /**
     * Метод получния списка событий по параметрам.
     * @param users числовые идектификаторы пользователей-инициаторов события;
     * @param states статусы событий;
     * @param categories категории событий;
     * @param rangeStart нижняя граница временного интервала для поиска событий;
     * @param rangeEnd верхняя граница врееменного интервала для поиска событий;
     * @param from номер страницы;
     * @param size размер страницы.
     * @return
     */
    List<EventFullDto> getAll(List<Long> users,
                              List<EventState> states,
                              List<Long> categories,
                              LocalDateTime rangeStart,
                              LocalDateTime rangeEnd,
                              int from,
                              int size);

    /**
     * Метод обновления уже существующего события.
     * @param eventDto DTO-объект с необходимой для обновления информацией;
     * @param eventId числовой идентификатор обновляемого события.
     * @return обновленное событие.
     */
    EventFullDto update(UpdateEventDto eventDto, long eventId);

    /**
     * Метож изменения статуса события на {@link ru.practicum.explorewithme.event.model.EventState#PUBLISHED}.
     * @param eventId числовой идентификатор события.
     * @return опубликованное событие.
     */
    EventFullDto publish(long eventId);

    /**
     * Метод изменения статуса события на {@link ru.practicum.explorewithme.event.model.EventState#CANCELED}.
     * @param eventId числовой идентификатор события.
     * @return отклоненное событие.
     */
    EventFullDto reject(long eventId);
}
