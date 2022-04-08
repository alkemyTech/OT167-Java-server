package com.alkemy.ong.service;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.exception.MessagePag;
import org.springframework.web.context.request.WebRequest;

public interface MemberService {
    MemberDto saveMember(MemberDto memberDto);

    MessagePag getAllMembers(int page, WebRequest request);

    void deleteMemberById(Long id);

    MemberDto updateMember(Long id, MemberDto memberDto);
}
