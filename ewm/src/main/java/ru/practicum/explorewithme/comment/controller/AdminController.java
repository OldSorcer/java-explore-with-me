package ru.practicum.explorewithme.comment.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentTextDto;
import ru.practicum.explorewithme.comment.service.AdminCommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/admin/comments")
@AllArgsConstructor
@Validated
@Slf4j
public class AdminController {
    private final AdminCommentService commentService;

    @PatchMapping("/{commentId}")
    public CommentDto update(@Valid @RequestBody CommentTextDto commentTextDto,
                             @PathVariable @Min(1) long commentId) {
        log.info("[ewm-service] Получен GET запрос к эндпоинту /admin/comments/{} \n" +
                "Тело запроса: {}",
                commentId, commentTextDto);
        return commentService.update(commentTextDto, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable @Min(1) long commentId) {
        log.info("[ewm-service] Получен DELETE запрос к эндпоинту /admin/comments/{}",
                commentId);
        commentService.delete(commentId);
    }
}
