package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.model.Comment;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);

    CommentDto updateComment(Long id, CommentDto commentDto);

    List<CommentDto> getAllCommentsByIdNews(Long id);

    void delete(Authentication aut, Long id);

    void existId(Long id);

}
