package ru.practicum.explorewithme.comment.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.comment.service.AdminCommentService;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/admin/comments")
@AllArgsConstructor
@Validated
@Slf4j
public class AdminController {
    private final AdminCommentService commentService;

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable @Min(1) long commentId) {
        log.info("[ewm-service] Получен DELETE запрос к эндпоинту /admin/comments/{}",
                commentId);
        commentService.delete(commentId);
    }
}
