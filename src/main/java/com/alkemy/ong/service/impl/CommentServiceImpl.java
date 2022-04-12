package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MessageSource messageSource;
    private final NewsRepository newsRepository;


    @Override
    public Comment save(Comment comment) {
        if (comment != null) {

            if (comment.getBody() == null) {
                throw new NullPointerException(messageSource.getMessage("comment.not.null", null, Locale.ENGLISH));
            }

            return commentRepository.save(comment);

        } else {
            throw new NullPointerException(messageSource.getMessage("comment.not.null", null, Locale.ENGLISH));
        }
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        try {
            Comment commentExists = commentRepository.findById(id).get();

            Comment commentSaved = commentMapper.commentDto2Entity(commentDto);

            commentSaved.setId(commentExists.getId());

            return commentMapper.commentEntity2Dto(commentRepository.save(commentSaved));
        } catch (NoSuchElementException e) {
            throw new NotFoundException(messageSource.getMessage("comment.not.found", null, Locale.ENGLISH));
        }
    }

    @Override
    public List<CommentDto> getAllCommentsByIdNews(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("not.found", null, Locale.ENGLISH)));

        return commentRepository.findAll().stream().filter(comment -> comment.getNews_id().getId().equals(id)).collect(Collectors.toList())
                .stream().map(commentMapper::commentEntity2Dto).collect(Collectors.toList());
    }

    @Override
    public void delete(Authentication aut, Long id) {
        if (checkId(aut, id)) {
            Comment entity = commentRepository.getById(id);
            entity.setDeleted(true);
            commentRepository.save(entity);
        }
    }

    private boolean checkId(Authentication aut, Long id) {
        String username = aut.getName();
        var commentEntityOptional = commentRepository.findById(id);
        if (commentEntityOptional.isPresent()) {
            Comment comment = commentEntityOptional.get();
            String emailUserCreator = comment.getUser_id().getEmail();
            String authorityUser = String.valueOf(aut.getAuthorities().stream().count());
            if (username.equals(emailUserCreator) || authorityUser.equals("ROLE_ADMIN")) {
                return true;
            }
            else {
               return false;
            }
        } else {
            return false;
          }

    }

    @Override
    public void existId (Long id){
        if (!commentRepository.existsById(id)) {
                throw new NotFoundException(messageSource.getMessage("comment.not.found", null, Locale.ENGLISH));
        }
    }

}


