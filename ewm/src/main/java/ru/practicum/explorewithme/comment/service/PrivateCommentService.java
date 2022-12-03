package ru.practicum.explorewithme.comment.service;

import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentTextDto;
import ru.practicum.explorewithme.comment.model.CommentSort;

import java.util.List;

public interface PrivateCommentService {
    CommentDto create(CommentTextDto commentDto, long eventId, long userId);

    CommentDto update(CommentTextDto commentDto, long commentId, long userId);

    void delete(long userId, long commentId);

    List<CommentDto> getAllByUserId(long userId, int from, int size, CommentSort sort);
}
