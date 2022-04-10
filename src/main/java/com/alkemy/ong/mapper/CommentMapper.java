package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import com.alkemy.ong.security.model.UserEntity;
import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    public Comment commentDto2Entity(CommentDto commentDto){

        CommentDto commentValidated= Validate(commentDto);

        /*if(commentValidated.getNews_id()==null){
            throw new NullPointerException(messageSource.getMessage("news.not.null", null, Locale.ENGLISH));
        }

        if(commentValidated.getUser_id()==null){
            throw new NullPointerException(messageSource.getMessage("user.not.found", null, Locale.ENGLISH));
        }*/

        Comment newComment = new Comment();

        //newComment.setNews_id(newsService.findNewById(commentValidated.getNews_id()).get());
        //newComment.setUser_id(userService.findUserById(commentValidated.getUser_id()).get());
        newComment.setBody(commentDto.getBody());
        return newComment;

    }

    private CommentDto Validate(CommentDto commentDto){
        try{
            //UserEntity userEntity= userService.findUserById(commentDto.getUser_id()).get();

            //News newsEntity = newsService.findNewById(commentDto.getNews_id()).get();

            //commentDto.setUser_id(userEntity.getId());

            //commentDto.setNews_id(newsEntity.getId());

            return commentDto;
        }catch (NoSuchElementException e){
            CommentDto validated=new CommentDto();
            validated.setNews_id(null);
            validated.setUser_id(null);
            return validated;
        }
}

    public CommentDto commentEntity2Dto(Comment comment){

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        //commentDto.setNews_id(comment.getNews_id().getId());
        //commentDto.setUser_id(comment.getUser_id().getId());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    public List<CommentDto> listCommentsDto(List<Comment> commentsList){
        return commentsList.stream().map(comments -> commentEntity2Dto(comments)).collect(Collectors.toList());
    }
}
