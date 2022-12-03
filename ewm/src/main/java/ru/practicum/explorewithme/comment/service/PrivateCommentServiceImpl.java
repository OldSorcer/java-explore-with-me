package ru.practicum.explorewithme.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentTextDto;
import ru.practicum.explorewithme.comment.dto.mapper.CommentDtoMapper;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.comment.model.CommentSort;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;
import ru.practicum.explorewithme.exception.ForbiddenOperationException;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentDto create(CommentTextDto commentDto, long eventId, long userId) {
        User foundedUser = findUserById(userId);
        Event foundedEvent = findEventById(eventId);
        checkEventState(foundedEvent);
        Comment createdComment = CommentDtoMapper.toNewComment(commentDto);
        createdComment.setUser(foundedUser);
        createdComment.setEventId(foundedEvent.getId());
        return CommentDtoMapper.toCommentDto(commentRepository.save(createdComment));
    }

    @Override
    public CommentDto update(CommentTextDto commentDto, long commentId, long userId) {
        Comment foundedComment = findCommentById(commentId);
        if (foundedComment.getUser().getId() != userId) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно отредактировать комментарий оставленный другим пользователем");
        }
        foundedComment.setText(commentDto.getText());
        return CommentDtoMapper.toCommentDto(commentRepository.save(foundedComment));
    }

    @Override
    public void delete(long userId, long commentId) {
        Comment foundedComment = findCommentById(commentId);
        if (foundedComment.getUser().getId() != userId) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно удалить комментарий созданный другим пользователем");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getAllByUserId(long userId, int from, int size, CommentSort sort) {
        Pageable page;
        if (sort.equals(CommentSort.NEW)) {
            page = PageRequest.of(from, size, Sort.by("created").ascending());
        } else {
            page = PageRequest.of(from, size, Sort.by("created").descending());
        }
        return CommentDtoMapper.toCommentDto(commentRepository.findAllByUserId(userId, page));
    }

    private User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден",
                        String.format("Пользователь с ID %d не найден", userId)));
    }

    private Event findEventById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено",
                        String.format("Событие с ID %d не найдено", eventId)));
    }

    private void checkEventState(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenOperationException("Запрещенная операция",
                    "Невозможно оставить комментарий к неопубликованному или отмененному событию.");
        }
    }

    private Comment findCommentById(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Комментарий не найден",
                        String.format("Комментарий с ID %d не найден", commentId)));
    }
}
