package ru.practicum.explorewithme.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentTextDto;
import ru.practicum.explorewithme.comment.dto.mapper.CommentDtoMapper;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;

    @Override
    public CommentDto update(CommentTextDto commentDto, long commentId) {
        Comment foundedComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Комментарий не найден",
                        String.format("Комментарий с ID %d не найден")));
        foundedComment.setText(commentDto.getText());
        return CommentDtoMapper.toCommentDto(commentRepository.save(foundedComment));
    }

    @Override
    public void delete(long commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (EmptyResultDataAccessException exc) {
            throw new EntityNotFoundException("Комментарий не найден",
                    "Комментарий с ID %d не найден");
        }
    }
}
