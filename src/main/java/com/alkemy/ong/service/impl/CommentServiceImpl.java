package com.alkemy.ong.service.impl;

import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public Comment save(Comment comment) {

        if (comment!=null){

            if (comment.getNews_id()==null){
                throw new NullPointerException(messageSource.getMessage("news.not.null", null, Locale.ENGLISH));
            }

            if(comment.getUser_id()==null){
                throw new NullPointerException(messageSource.getMessage("user.not.found", null, Locale.ENGLISH));
            }

            if(comment.getBody()==null){
                throw new NullPointerException(messageSource.getMessage("comment.not.null", null, Locale.ENGLISH));
            }

            return commentRepository.save(comment);

        }else{
            throw new NullPointerException(messageSource.getMessage("comment.not.null", null, Locale.ENGLISH));
        }
    }
}
