package ru.practicum.explorewithme.comment.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.model.CommentSort;
import ru.practicum.explorewithme.comment.service.PublicCommentService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class PublicCommentController {
    private final PublicCommentService commentService;

    @GetMapping("/{commentId}")
    public CommentDto getById(@PathVariable long commentId) {
        return commentService.getById(commentId);
    }

    @GetMapping
    public List<CommentDto> getAllByEventId(@RequestParam @Min(1) long eventId,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "NEW") CommentSort sort) {
        return commentService.getAllByEventId(eventId, from, size, sort);
    }
}