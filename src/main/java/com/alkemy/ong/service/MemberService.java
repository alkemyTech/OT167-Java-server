package com.alkemy.ong.service;
import com.alkemy.ong.dto.MemberDto;

import java.util.Map;

public interface MemberService {
    MemberDto saveMember(MemberDto memberDto);
    Map<String, String> deleteMemberById(Long id);
}
