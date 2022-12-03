package ru.practicum.explorewithme.comment.dto.mapper;

import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentTextDto;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.user.dto.mapper.UserDtoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public final class CommentDtoMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .eventId(comment.getEventId())
                .user(UserDtoMapper.toUserShortDto(comment.getUser()))
                .build();
    }

    public static List<CommentDto> toCommentDto(List<Comment> comments) {
        return comments.stream()
                .map(CommentDtoMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public static Comment toNewComment(CommentTextDto commentTextDto) {
        return Comment.builder()
                .text(commentTextDto.getText())
                .created(LocalDateTime.now())
                .build();
    }
}
