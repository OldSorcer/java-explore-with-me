package ru.practicum.explorewithme.comment.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentTextDto;
import ru.practicum.explorewithme.comment.model.CommentSort;
import ru.practicum.explorewithme.comment.service.PrivateCommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/comments")
@AllArgsConstructor
@Slf4j
public class PrivateCommentController {
    private final PrivateCommentService commentService;

    @PostMapping("/{eventId}")
    public CommentDto create(@RequestBody @Valid CommentTextDto commentTextDto,
                             @PathVariable @Min(1) long userId,
                             @PathVariable @Min(1) long eventId) {
        log.info("[ewm-service] Получен POST запрос к эндпоинту /users/{}/comments/{}\n" +
                        "Тело запроса: {}",
                userId, eventId, commentTextDto);
        return commentService.create(commentTextDto, eventId, userId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto update(@RequestBody @Valid CommentTextDto commentTextDto,
                             @PathVariable @Min(1) long userId,
                             @PathVariable @Min(1) long commentId) {
        log.info("[ewm-service] Получен PATCH запрос к эндпоинту /users/{}/comments{}\n" +
                        "Тело запроса: {}",
                userId, commentId, commentTextDto);
        return commentService.update(commentTextDto, commentId, userId);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable @Min(1) long userId,
                       @PathVariable @Min(1) long commentId) {
        log.info("[ewm-service] Получен DELETE запрос к эндпоинту /users/{}/comments{}",
                userId, commentId);
        commentService.delete(userId, commentId);
    }

    @GetMapping
    public List<CommentDto> getAllByUserId(@PathVariable @Min(1) long userId,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "NEW") CommentSort sort) {
        log.info("[ewm-service] Получен GET запрос к эндроинту /users/{}/comments?from={}&size={}&sort={}",
                userId, from, size, sort);
        return commentService.getAllByUserId(userId, from, size, sort);
    }
}