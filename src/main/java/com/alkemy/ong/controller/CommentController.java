package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Add new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment save successfully",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content),
            @ApiResponse(responseCode = "400", description = "The given id must not be null!", content = @Content)
            
    })
    @PostMapping
    public ResponseEntity<?> addNewComment(@Valid @RequestBody CommentDto commentDto) {
            CommentDto commentSaved = commentService.save(commentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(commentSaved);
    }

    @Operation(summary = "Get comment by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get comment",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
            
    })
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<?> getCommentsByIdNews(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByIdNews(id));
    }

    @Operation(summary = "Update comment by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content),
            @ApiResponse(responseCode = "400", description = "The given id must not be null!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
            
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        try {
            return ResponseEntity.ok(commentService.updateComment(id, commentDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete comment by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully",content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
            
    })
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

    @Operation(summary = "Get all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully get all comments",content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessagePag.class))}),
            @ApiResponse(responseCode = "403", description = "You do not have the permissions to enter", content = @Content)                     
    })
    @GetMapping
    public ResponseEntity<?> getAllComments(@RequestParam(value = "page", required = true) String page, WebRequest request) {
        MessagePag commentsList = commentService.getAllComments(Integer.parseInt(page), request);
        return ResponseEntity.ok().body(commentsList);
    }

}
