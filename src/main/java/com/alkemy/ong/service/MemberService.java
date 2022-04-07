package com.alkemy.ong.service;
import com.alkemy.ong.dto.MemberDto;

import java.util.List;

public interface MemberService {
    MemberDto saveMember(MemberDto memberDto);

    List<MemberDto> listAllMembers();

    void deleteMemberById(Long id);

    MemberDto updateMember(Long id, MemberDto memberDto);
}
