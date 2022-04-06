package com.alkemy.ong.service;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;

import java.util.List;

public interface MemberService {
    MemberDto saveMember(MemberDto memberDto);

    List<MemberDto> listAllMembers();

    void deleteMemberById(Long id);

}
