package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.model.Comment;

public interface CommentService {
    Comment save(Comment comment);

    CommentDto updateComment(Long id, CommentDto commentDto);
}
