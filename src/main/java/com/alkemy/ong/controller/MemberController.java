package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MessageSource messageSource;

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<Map<String,String>> memberCreate(@Valid @RequestBody MemberDto memberDto) {
        memberService.saveMember(memberDto);
        Map<String, String> message = new HashMap<>(){{put("message: ", messageSource.getMessage("member.created.successfully",new Object[]{memberDto.getName()}, Locale.ENGLISH));}};
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<?> listMembers(@RequestParam(value = "page", required = true) String page, WebRequest request){

        return ResponseEntity.ok(memberService.getAllMembers(Integer.parseInt(page),request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Map<String,String>> memberDelete(@PathVariable String id){
        memberService.deleteMemberById(Long.valueOf(id));
        return ResponseEntity.ok().body(new HashMap<>(){{put("message", messageSource.getMessage("member.delete.successfully", new Object[]{id.toString()}, Locale.ENGLISH));}});
    }
    @PutMapping(value = "{id}")
    public ResponseEntity<MemberDto> memberUpdate(@PathVariable String id, @RequestBody MemberDto memberDto){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(Long.valueOf(id), memberDto));
    }
}
