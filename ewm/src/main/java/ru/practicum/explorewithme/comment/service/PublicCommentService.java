package ru.practicum.explorewithme.comment.service;

import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.model.CommentSort;

import java.util.List;

public interface PublicCommentService {
    CommentDto getById(long commentId);
    List<CommentDto> getAllByEventId(long eventId, int from, int size, CommentSort sort);
}
