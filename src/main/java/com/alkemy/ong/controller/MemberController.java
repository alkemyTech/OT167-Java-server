package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
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
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Map<String,String>> memberDelete(@PathVariable String id){
        return ResponseEntity.ok().body(memberService.deleteMemberById(Long.valueOf(id)));
    }
}
