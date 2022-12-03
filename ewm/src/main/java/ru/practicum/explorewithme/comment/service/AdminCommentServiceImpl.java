package ru.practicum.explorewithme.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;

    @Override
    public void delete(long commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (EmptyResultDataAccessException exc) {
            throw new EntityNotFoundException("Комментарий не найден",
                    String.format("Комментарий с ID %d не найден", commentId));
        }
    }
}
