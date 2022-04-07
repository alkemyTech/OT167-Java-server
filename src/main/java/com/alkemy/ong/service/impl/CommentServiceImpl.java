package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MessageSource messageSource;

    @Override
    public Comment save(Comment comment) {
        if (comment!=null){

            if(comment.getBody()==null){
                throw new NullPointerException(messageSource.getMessage("comment.not.null", null, Locale.ENGLISH));
            }

            return commentRepository.save(comment);

        }else{
            throw new NullPointerException(messageSource.getMessage("comment.not.null", null, Locale.ENGLISH));
        }
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        try{
            Comment commentExists=commentRepository.findById(id).get();

            Comment commentSaved= commentMapper.commentDto2Entity(commentDto);

            commentSaved.setId(commentExists.getId());

            return commentMapper.commentEntity2Dto(commentRepository.save(commentSaved));
        }catch (NoSuchElementException e){
            throw new NotFoundException(messageSource.getMessage("comment.not.found", null,Locale.ENGLISH));
        }
    }
}
