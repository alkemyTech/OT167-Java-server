package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Locale;
@Tag(name = "Comment")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<?> addNewComment(@Valid @RequestBody CommentDto commentDto) {
            CommentDto commentSaved = commentService.save(commentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(commentSaved);
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<?> getCommentsByIdNews(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByIdNews(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        try {
            return ResponseEntity.ok(commentService.updateComment(id, commentDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Authentication aut, @PathVariable Long id) {
        commentService.existId(id);
        try {
            commentService.delete(aut, id);
            return ResponseEntity.ok(messageSource.getMessage("comment.delete.successfully", null, Locale.ENGLISH));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllComments(@RequestParam(value = "page", required = true) String page, WebRequest request) {
        MessagePag commentsList = commentService.getAllComments(Integer.parseInt(page), request);
        return ResponseEntity.ok().body(commentsList);
    }

}
