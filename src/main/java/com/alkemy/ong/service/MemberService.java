package com.alkemy.ong.service;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

public interface MemberService {
    MemberDto saveMember(MemberDto memberDto);

    MessagePag getAllMembers(int page, WebRequest request);

    void deleteMemberById(Long id);

}
