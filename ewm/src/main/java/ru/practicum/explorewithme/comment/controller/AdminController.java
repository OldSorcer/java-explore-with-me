package ru.practicum.explorewithme.comment.controller;

import lombok.AllArgsConstructor;
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
public class AdminController {
    private final AdminCommentService commentService;

    @PatchMapping("/{commentId}")
    public CommentDto update(@Valid @RequestBody CommentTextDto commentTextDto,
                             @PathVariable @Min(1) long commentId) {
        return commentService.update(commentTextDto, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable @Min(1) long commentId) {
        commentService.delete(commentId);
    }
}
