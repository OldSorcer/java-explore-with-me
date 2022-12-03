package ru.practicum.explorewithme.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.mapper.CommentDtoMapper;
import ru.practicum.explorewithme.comment.model.CommentSort;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.exception.EntityNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService{
    private final CommentRepository commentRepository;


    @Override
    public CommentDto getById(long commentId) {
        return CommentDtoMapper.toCommentDto(commentRepository.findById(commentId).orElseThrow(() ->
                new EntityNotFoundException("Комментарий не найден",
                        String.format("Комментарий с ID %d не найден", commentId))));
    }

    @Override
    public List<CommentDto> getAllByEventId(long eventId, int from, int size, CommentSort sort) {
        Pageable page;
        if (sort.equals(CommentSort.NEW)) {
            page = PageRequest.of(from, size, Sort.by("created").descending());
        } else {
            page = PageRequest.of(from, size, Sort.by("created").ascending());
        }
        return CommentDtoMapper.toCommentDto(commentRepository.findAllByEventId(eventId, page));
    }
}
