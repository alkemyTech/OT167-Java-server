package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    @GetMapping
    public ResponseEntity<List<MemberDto>> listMembers(){
        return ResponseEntity.ok(memberService.listAllMembers());
    }
}
