package ru.practicum.explorewithme.comment.service;

import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentTextDto;

public interface AdminCommentService {
    CommentDto update(CommentTextDto commentDto, long commentId);
    void delete(long commentId);
}
