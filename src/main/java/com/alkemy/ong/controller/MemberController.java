package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.exception.MessageInfo;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.exception.MessageResponse;
import com.alkemy.ong.exception.MessagesInfo;
import com.alkemy.ong.service.MemberService;

import com.alkemy.ong.utils.SwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    

    private final MessageSource messageSource;

    @Autowired
    private MessageResponse messageResponse;
    @Autowired
    private MemberService memberService;


    @Operation(summary = "create a new member", description = "Create a member with filling the params of the body and return a success or error message. \n" +
            "The required fields are: name and image. The name field can only be filled with non-numeric characters.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Member was created",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageInfo.class),
                    array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {
                            @ExampleObject(name = "Example 1", summary = "Member created", description = "when the required fields are filled in, sends a member message successfully created", value = SwaggerConstants.MODEL_MEMBER_CREATED),
                    }) }),
    @ApiResponse(responseCode = "400", description = "A param error",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessagesInfo.class),
                    examples = {
                            @ExampleObject(name = "Example 1", summary = "Member param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = SwaggerConstants.MODEL_MEMBER_PARAM_ERROR),
                    })})})
    @PostMapping
    public ResponseEntity<MessageInfo> memberCreate(@Parameter(description = "Fill all the parrameters cannot be null to create or update a member") @Valid @RequestBody MemberDto memberDto, WebRequest request) {
        memberService.saveMember(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse.messageOk(messageSource.getMessage("member.created.successfully",new Object[]{memberDto.getName()}, Locale.ENGLISH), HttpStatus.CREATED.value(), request));
    }

    @Operation(summary ="list of all members (Only Admin)", description = "Shows a list of 10 members, if they not exists shows a message of page empty")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of 10 members or message of empty List",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessagePag.class),
                    array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {
                            @ExampleObject(name = "Example success", summary = "Member list", description = "when a page number is entered, a list of 10 members of the requested page is displayed", value = SwaggerConstants.MODEL_MEMBER_PAGE),
                            @ExampleObject(name = "Example not found", summary = "Empty list", description = "If the page is empty an empty list message is displayed", value = SwaggerConstants.MODEL_MEMBER_EMPTY_ERROR)
                    }
            ) }),
    @ApiResponse(responseCode = "400", description = "Error for the character entered",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageInfo.class),
                    examples = {@ExampleObject(name = "Example error 400", summary = "Error character", description = "When I enter an invalid character in the path it sends an error message 400 (Bad Request).", value = SwaggerConstants.MODEL_MEMBER_ERROR_400
                    )}) })})


    @GetMapping()
    public ResponseEntity<MessagePag> listMembers(@Parameter(description = "Page number to list members", example = "1") @RequestParam(value = "page", required = true) String page, WebRequest request){
        return ResponseEntity.ok(memberService.getAllMembers(Integer.parseInt(page),request));
    }

    @Operation(summary = "Delete a member with the id (Only Admin)", description = "Find a member with the id and try delete it and return a success or error message")
    @ApiResponse(responseCode = "200", description = "Member was deleted",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = MessageInfo.class),
                    examples = {@ExampleObject(name = "Member deleted", summary = "Member deleted", description = "The member is successfully removed when found by id, then a success message is sent.", value = SwaggerConstants.MODEL_MEMBER_DELETE
                    )}) })
    @ApiResponse(responseCode = "400", description = "Error for the character entered",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageInfo.class),
                    examples = {@ExampleObject(name = "Example error 400", summary = "Error character", description = "When I enter an invalid character in the path it sends an error message 400 (Bad Request).", value = SwaggerConstants.MODEL_MEMBER_ERROR_400
                    )}) })
    @ApiResponse(responseCode = "404", description = "Member not found",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageInfo.class),
                    examples = {@ExampleObject(name = "Example error 404", summary = "Member not found", description = "When you enter the id of a nonexistent member in the route, it sends a message error 400 (Not Found).", value = SwaggerConstants.MODEL_MEMBER_ERROR_404
    )}) })


    @DeleteMapping("/{id}")
    public ResponseEntity<MessageInfo> memberDelete(@Parameter(description = "Id of the memeber to be delete", example = "1") @PathVariable String id, WebRequest request){
        memberService.deleteMemberById(Long.valueOf(id));
        return ResponseEntity.ok().body(messageResponse.messageOk(messageSource.getMessage("member.delete.successfully",new Object[]{id}, Locale.ENGLISH), HttpStatus.OK.value(), request));
    }


    @Operation(summary = "Update a member with the id", description = "Find a member with the id, fill the params of the body and return a member update or error message. \n" +
            "The required fields are: name and image. The name field can only be filled with non-numeric characters.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Member was modifed",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MemberDto.class),
                    examples = {@ExampleObject(name = "Member was update", summary = "Member update", description = "when the required fields are completed, the user is updated and the entity update is sent.", value = SwaggerConstants.MODEL_MEMBER
    )})}),
    @ApiResponse(responseCode = "400", description = "A param cannot be null",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = MessagesInfo.class),
            examples = {
                    @ExampleObject(name = "Example 1", summary = "Member param Error", description = "when required fields are not filled in, sends a 400 (Bad Request) error message", value = SwaggerConstants.MODEL_MEMBER_PARAM_ERROR),
            })}),
    @ApiResponse(responseCode = "404", description = "Member not found",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageInfo.class),
                    examples = {@ExampleObject(name = "Example error 404", summary = "Member not found", description = "When you enter the id of a nonexistent member in the route, it sends a message error 400 (Not Found).", value = SwaggerConstants.MODEL_MEMBER_ERROR_404
                    )}) })})
    @PutMapping(value = "{id}", produces = { "application/json" })
    public ResponseEntity<MemberDto> memberUpdate(
            @Parameter(description = "Id of the member to be update", example = "1") @PathVariable String id, @Parameter(description = "Fill all the parrameters cannot be null to create or update a member")  @RequestBody MemberDto memberDto){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(Long.valueOf(id), memberDto));
    }
}
