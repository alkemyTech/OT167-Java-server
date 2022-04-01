package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    @Autowired
    private CommentService commentService;

    public Comment commentDto2Entity(CommentDto commentDto){

        Comment newComment = new Comment();

        News newsId= new News();

        UserEntity userId = new UserEntity();

        newsId.setId(commentDto.getNews_id());

        userId.setId(commentDto.getUser_id());

        newComment.setNews_id(newsId);
        newComment.setUser_id(userId);
        newComment.setBody(commentDto.getBody());
        return newComment;

    }

    public CommentDto commentEntity2Dto(Comment comment){

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setNews_id(comment.getNews_id().getId());
        commentDto.setUser_id(comment.getUser_id().getId());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
