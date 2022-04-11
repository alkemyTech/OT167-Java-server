package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.MessagesInfo;
import com.alkemy.ong.exception.PaginationMessage;
import com.alkemy.ong.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
@Validated
public class NewsController {
    private final String MODEL_NEW = "{\"id\" : \"1\",\n \"name\" : \"final exam\",\n \"content\" : \"the final exam will be in 30 days\",\n \"image\" : \"imageUrl\",\n  \"creationDate\": \"2022-04-10T20:48:18.617Z\",\n" + "  \"updateDate\": \"2022-04-10T20:48:18.618Z\"\n}";
    private final String MODEL_NEW_CREATED = "{\n" + "  \"message\": \"The new was created successfully.\",\n" + "  \"status_code\": 200,\n" + "  \"path\": \"/news\"\n" + "}";
    private final String MODEL_NEW_SAVED = "{\n" +
            "    \"id\": 4,\n" +
            "    \"name\": \"nameOfTheNew\",\n" +
            "    \"content\": \"contentOfTheNew\",\n" +
            "    \"image\": \"imageUrl\",\n" +
            "    \"categoryId\": {\n" +
            "        \"idCategories\": 1,\n" +
            "        \"name\": \"nameOfTheCategory\",\n" +
            "        \"description\": \"descOfTheCategory\",\n" +
            "        \"image\": \"imageURL\",\n" +
            "        \"deleted\": false,\n" +
            "        \"creationDate\": \"10-04-2022 00:00:00\",\n" +
            "        \"updateDate\": \"10-04-2022 00:00:00\"\n" +
            "    },\n" +
            "    \"deleted\": false,\n" +
            "    \"creationDate\": \"10-04-2022 21:36:45\",\n" +
            "    \"updateDate\": \"10-04-2022 22:16:43\"\n" +
            "}";
    private final String MODEL_NEW_PARAM_ERROR = "{\n" + "  \"message\": {\n" + " \"image\": \"Image cannot be null\"" + "  },\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/news\"\n" + "}";
    private final String MODEL_NEW_FOUND= MODEL_NEW;
    private final String MODEL_NEW_NOT_FOUND= "{\n" + "  \"message\": \"The new has been not found.\", \n" + "  \"status_code\": 404,\n" + "  \"path\": \"/news/{id}\"\n" + "}";
    private final String MODEL_NEW_DELETED= "{\n" + "  \"message\": \"News delete ok.\" \n" + "}";
    private final String MODEL_NEW_LIST_FOUND= "{\n" + "    \"content\": [ \n"+ MODEL_NEW +",\n"+ MODEL_NEW + "\n    ],\n    \"status_code\": 202,\n    \"nextPath\": \"there's not page next\",\n    \"prevPath\": \"there's not page prev\" \n}" ;


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
                                    @ExampleObject(name = "Example 1", summary = "New created", description = "when the required fields are filled in, sends a new message successfully created", value = MODEL_NEW_CREATED)
                            }
                    )
            }),
            @ApiResponse(responseCode = "400", description = "A param error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = MODEL_NEW_PARAM_ERROR),
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
                                    @ExampleObject(name = "Example 1", summary = "New has been found", description = "when the new has succesfully founded.", value = MODEL_NEW_FOUND)
                            }
                    )
            }),
            @ApiResponse(responseCode = "400", description = "A param error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = MODEL_NEW_NOT_FOUND),
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
                                    @ExampleObject(name = "Example 1", summary = "New has been successfully deleted", description = "when the new has succesfully deleted.", value = MODEL_NEW_DELETED)
                            }
                    )
            }),
            @ApiResponse(responseCode = "400", description = "A param error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = MODEL_NEW_NOT_FOUND),
                            })})
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
                                    @ExampleObject(name = "Example 1", summary = "News founded", description = "when the news has successfully founded.", value = MODEL_NEW_LIST_FOUND)
                            }
                    )
            })
    })
    @GetMapping("/query")
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
                                    @ExampleObject(name = "Example 1", summary = "New has been successfully changed", description = "when the new has successfully saved his changes.", value = MODEL_NEW_SAVED)
                            }
                    )
            }),
            @ApiResponse(responseCode = "400", description = "A param error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessagesInfo.class),
                            examples = {
                                    @ExampleObject(name = "Example 1", summary = "New param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = MODEL_NEW_PARAM_ERROR),
                            })})
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editNews(@PathVariable Long id, @Valid @RequestBody NewsDto newsDto){
       return ResponseEntity.ok(newsService.updateNews(id, newsDto));
    }
}
