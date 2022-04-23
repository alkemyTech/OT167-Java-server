package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.MessagesInfo;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.utils.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
@Tag(name = "News")
@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
@Validated
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private MessageSource messageSource;


    @Operation(summary = "Save a New", description = "Create a new with filling the params of the body and return a success or error message. \n" +
            "The required fields are: name,content and image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New created", description = "when the required fields are filled in, sends a new message successfully created", value = SwaggerConstants.MODEL_NEW_CREATED)
                            }
                    )
            }),
            @ApiResponse(responseCode = "400", description = "A param error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = SwaggerConstants.MODEL_NEW_PARAM_ERROR),
                            })})
    })
    @PostMapping
    public ResponseEntity<NewsDto> save(@Valid @RequestBody NewsDto newsDto){
        NewsDto newsSaved = newsService.save(newsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsSaved);
    }

    @Operation(summary = "Find a new", description = "Find a new by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New has been found", description = "when the new has succesfully founded.", value = SwaggerConstants.MODEL_NEW_FOUND)
                            }
                    )
            }),
            @ApiResponse(responseCode = "400", description = "A param error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = SwaggerConstants.MODEL_NEW_NOT_FOUND),
                            })})
    })
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> findNewsById(@PathVariable Long id){
        NewsDto newsDto = newsService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(newsDto);
    }

    @Operation(summary = "Delete a new", description = "Delete a new by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New has been successfully deleted", description = "when the new has succesfully deleted.", value = SwaggerConstants.MODEL_NEW_DELETED)
                            }
                    )
            }),
            @ApiResponse(responseCode = "400", description = "A param error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = SwaggerConstants.MODEL_NEW_NOT_FOUND),
                            })})
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        this.newsService.delete(id);
        Map<String, String> message = new HashMap<>() {{
            put("message: ", messageSource
                    .getMessage("news.delete.ok", new Object[]{id}, Locale.ENGLISH));
        }};
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @Operation(summary = "Find news", description = "Find a pagination of 10 news and the link to the next and previous page.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "News founded", description = "when the news has successfully founded.", value = SwaggerConstants.MODEL_NEW_LIST_FOUND)
                            }
                    )
            })
    })
    @GetMapping
    public ResponseEntity<?> findAllNewsPag(@RequestParam(value = "page", required = true) String page, WebRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(newsService.findAllPag(Integer.parseInt(page), request));
    }

    @Operation(summary = "Edit a new", description = "Edit the content of a new, filling by his id, the required fields are: name,content and image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New has been successfully changed", description = "when the new has successfully saved his changes.", value = SwaggerConstants.MODEL_NEW_SAVED)
                            }
                    )
            }),
            @ApiResponse(responseCode = "400", description = "A param error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = SwaggerConstants.MODEL_NEW_PARAM_ERROR),
                            })})
    })

    @PutMapping("/{id}")
    public ResponseEntity<?> editNews(@PathVariable Long id, @Valid @RequestBody NewsDto newsDto){
       return ResponseEntity.ok(newsService.updateNews(id, newsDto));
    }
}
