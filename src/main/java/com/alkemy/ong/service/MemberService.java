package com.alkemy.ong.service;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    MemberDto saveMember(MemberDto memberDto);

    Page<MemberDto> getAllMembers(Pageable pageable);

    void deleteMemberById(Long id);

}
