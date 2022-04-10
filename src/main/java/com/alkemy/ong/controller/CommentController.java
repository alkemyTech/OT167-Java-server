package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

import java.util.ArrayList;

import static com.amazonaws.services.elasticbeanstalk.model.ConfigurationOptionValueType.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<?> getCommentsByIdNews(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByIdNews(id));
    }

    @PostMapping
    public ResponseEntity<?> addNewComment(@Valid @RequestBody CommentDto commentDto){
        try{
            Comment commentSaved = commentService.save(commentMapper.commentDto2Entity(commentDto));
            CommentDto commentDtoResponse = commentMapper.commentEntity2Dto(commentSaved);
            return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoResponse);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,@RequestBody CommentDto commentDto){
        try{
            return ResponseEntity.ok(commentService.updateComment(id,commentDto));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/query")
    public ResponseEntity<?> getAllComments(@RequestParam(value = "page", required = true) String page, WebRequest request){

        MessagePag commentsList = commentService.getAllComments(Integer.parseInt(page),request);

        return ResponseEntity.ok().body(commentsList);

    }
}
