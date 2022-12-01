package ru.practicum.explorewithme.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.client.dto.EndpointHitDto;
import ru.practicum.explorewithme.client.dto.ViewStatsDto;
import ru.practicum.explorewithme.event.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, расширяющий базовый клиент. Обладает свойствами:
 * <br><b>prefixPost</b> - конечный эндпоинт для POST запроса;
 * <br><b>prefixGet</b> - конечный эндпоинт для GET запроса с параметрами.
 * @see ru.practicum.explorewithme.client.BaseClient
 */
@Component
public class StatsClient extends BaseClient {
    private final String prefixPost = "/hit";
    private final String prefixGet = "/stats?start={start}&end={end}&unique={unique}&uris={uris}";
    private final ObjectMapper om;
    @Value("Explore-With-Me")
    private String app;

    @Autowired
    public StatsClient(@Value("${stats.server-url}") String url, ObjectMapper om) {
        super(url);
        this.om = om;
    }

    /**
     * Метод сохранения обращения к эндпоинту в сервис статистики.
     * @param request запрос, полученный к основному сервису.
     */
    public void save(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        post(prefixPost, new EndpointHitDto(app, uri, ip));
    }

    /**
     * Метод получения просмотров событий из сервиса стистики.
     * @param events список событий, для которых необходимо получить просмотры;
     * @param unique флаг, определяющий уникальность запросов.
     * @return список объектов,
     * описывающих ститистику просмотров {@link ru.practicum.explorewithme.client.dto.ViewStatsDto}
     */
    public List<ViewStatsDto> getViews(List<Event> events, boolean unique) {
        Map<String, Object> parameters = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String[] uris = events.stream().map(this::createUriFromEvent).toArray(String[]::new);
        LocalDateTime start = events.stream().map(Event::getCreatedOn).min(LocalDateTime::compareTo).orElseThrow();
        parameters.put("start", URLEncoder.encode(formatter.format(start), StandardCharsets.UTF_8));
        parameters.put("end", URLEncoder.encode(formatter.format(LocalDateTime.now()), StandardCharsets.UTF_8));
        parameters.put("unique", unique);
        parameters.put("uris", uris);
        try {
            return Arrays.asList(om.readValue(get(prefixGet, parameters).getBody(), ViewStatsDto[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод преобразует объект события в строковое представление, необходимое для получения статисики из
     * соответствующего сервиса.
     * @param event объект события.
     * @return строковое представление обращения к событию сохраненное в сервисе статистики.
     */
    private String createUriFromEvent(Event event) {
        return "/events/" + event.getId();
    }
}