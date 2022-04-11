package com.alkemy.ong.controller;
import com.alkemy.ong.exception.*;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.PaginationMessage;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Create, show, delete and update Categories")
public class CategoryController {
    private final String MODEL_CATEGORY = "{\"idCategories\" : \"1\", \"name\" : \"categoryOne\", \"description\" : \"category1\", \"image\" : \"imageCat1\",\n" + "\"creationDate\" : \"2022-04-09\",\n" + "\"updateDate\" : \"2022-04-09\"}\n";
    private final String MODEL_LIST_NAME = "{\"name\" : \"categoryOne\"}\n";
    private final String MODEL_MESSAGE_LIST = "{\n" + "  \"content\": [\n" + MODEL_LIST_NAME + "  ],\n" + "  \"status_code\": 200\n" + "}";
    private final String MODEL_MESSAGE_ERROR_EMPTY = "{\n" + "  \"content\": [\n" + " \"There is a empty page.\"" +"  ],\n" + "  \"status_code\": 200\n" +"}";
    private final String MODEL_ERROR_400 = "{\n" + "  \"message\": \"Required id field is not a number\",\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/categories\"\n" + "}";
    private final String MODEL_ERROR_404 = "{\n" + "  \"message\": \"The ID doesn't exist.\",\n" + "  \"status_code\": 404,\n" + "  \"path\": \"/categories\"\n" + "}";
    private final String MODEL_CATEGORY_CREATED = "{\n" + "  \"message\": \"The category was created successfully.\",\n" + "  \"status_code\": 201,\n" + "  \"path\": \"/categories\"\n" + "}";
    private final String MODEL_DELETE = "{\n" + "  \"message\": \"The category was deleted.\",\n" + "  \"status_code\": 200,\n" + "  \"path\": \"/categories\"\n" + "}";

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private MessageSource messageSource;

    private PaginationMessage paginationMessage;


    @Tag(name = "Categories")
    @Operation(summary = "Get list of categories by names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageInfo.class),
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {
                                    @ExampleObject(name = "Example success", summary = "Category list", description = "List of categories by name is displayed", value = MODEL_MESSAGE_LIST),
                                    @ExampleObject(name = "Example not found", summary = "Empty list", description = "empty list message", value = MODEL_MESSAGE_ERROR_EMPTY)
                            })}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(schema = @Schema(implementation = MessageInfo.class),
                            examples = @ExampleObject(name = "Message of error", summary = "400 from the server.", description = "Invalid character in request", value = MODEL_ERROR_400))})
    })
    @GetMapping
    public ResponseEntity<List<String>> listCategoriesByName(){
        return ResponseEntity.ok().body(categoryService.getAllCategoriesByName());
    }

    @Tag(name = "Categories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found category",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageInfo.class),
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {
                                    @ExampleObject(name = "Example success", summary = "Found category", description = "Category found and success message", value = MODEL_CATEGORY),
                            })}),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = {@Content(schema = @Schema(implementation = MessageInfo.class),
                            examples = @ExampleObject(name = "Message of error", summary = "404 from the server.", value = MODEL_ERROR_404))})
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getCategory(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.categoryEntity2Dto(categoryService.findById(id).get()));
    }

    @Tag(name = "Categories")
    @Operation(summary = "Add a new category to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create category",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageInfo.class))}),

            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = {@Content(schema = @Schema(implementation = MessageInfo.class),
                            examples = @ExampleObject(name = "Message of error", summary = "404 from the server.", value = MODEL_ERROR_404))})
    })
    @PostMapping
    public ResponseEntity<CategoryDto> addNewCategory(@Valid @RequestBody CategoryDto categoryDto) throws DataAlreadyExistException, IncorrectPatternExeption {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryDto));
    }

    @Tag(name = "Categories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category update",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageInfo.class),
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {
                                    @ExampleObject(name = "Example success", summary = "Category update", description = "Show updated category", value = MODEL_CATEGORY)})}),
            @ApiResponse(responseCode = "400", description = "Invalid field",
                    content = {@Content(schema = @Schema(implementation = MessageInfo.class),
                            examples = @ExampleObject(name = "Message of error 400", summary = "400 from the server.", description = "Invalid character in request", value = MODEL_ERROR_400))}),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = {@Content(schema = @Schema(implementation = MessageInfo.class),
                            examples = @ExampleObject(name = "Message of error 404", summary = "404 from the server, Category not found.", description = "The ID doesn't exist.", value = MODEL_ERROR_404))})
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,@RequestBody Category category){
        try{
            Category categoryUpdated= categoryService.updateCategory(id,category);
            CategoryDto categoryDtoResponse = categoryMapper.categoryEntity2Dto(categoryUpdated);
            return ResponseEntity.ok(categoryDtoResponse);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(category);
        }
    }

    @Tag(name = "Categories")
    @Operation(summary = "Delete category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the category",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageInfo.class),
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {
                                    @ExampleObject(name = "Example success", summary = "Deleted category", description = "Category deleted successfully.", value = MODEL_DELETE)})}),
            @ApiResponse(responseCode = "400", description = "Invalid field",
                    content = {@Content(schema = @Schema(implementation = MessageInfo.class),
                            examples = @ExampleObject(name = "Message of error 400", summary = "400 from the server.", description = "Invalid character in request", value = MODEL_ERROR_400))}),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = {@Content(schema = @Schema(implementation = MessageInfo.class),
                            examples = @ExampleObject(name = "Message of error 404", summary = "404 from the server, Category not found.", description = "The ID doesn't exist.", value = MODEL_ERROR_404))})
    })
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> categoryDelete(@PathVariable String id){
        categoryService.deleteCategoryById(Long.valueOf(id));
        Map<String, String> message = new HashMap<>(){{put("message: ", messageSource
                .getMessage("category.delete.successfully", new Object[]{id}, Locale.ENGLISH));}};
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/query")
    public ResponseEntity<?> findAllCategoryPag(@RequestParam(value = "page", required = true) String page, WebRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllPag(Integer.parseInt(page), request));
    }
}
