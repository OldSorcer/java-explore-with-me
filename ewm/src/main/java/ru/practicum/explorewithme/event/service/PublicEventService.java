package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.model.EventSort;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    /**
     * Метод получения всех событий с параметрами поиска.
     * @param text текст или фрагмент текста в аннотации или описании события;
     * @param categories список числовых идентификаторов категорий событий;
     * @param paid параметр определяющий требуется ли оплата за участие в событии;
     * @param rangeStart нижняя граница временного интервала для поиска событий;
     * @param rangeEnd верхняя граница временного интервала для поиска событий;
     * @param onlyAvailable только доступные события;
     * @param eventSort способ сортировки событий;
     * @param from номер первой страницы списка событий;
     * @param size размер страницы;
     * @param request запрос, полученный от клиента.
     * @return список DTO-объектов с кратким описанием событий.
     */
    List<EventShortDto> getAll(String text,
                               List<Long> categories,
                               Boolean paid,
                               LocalDateTime rangeStart,
                               LocalDateTime rangeEnd,
                               Boolean onlyAvailable,
                               EventSort eventSort,
                               int from,
                               int size,
                               HttpServletRequest request);

    /**
     * Метод получения события по числовому идентификатору.
     * @param request запрос клиента;
     * @param eventId числовой идентификатор события.
     * @return DTO-объект с полной информацией о событии.
     */
    EventFullDto getById(HttpServletRequest request, long eventId);
}
