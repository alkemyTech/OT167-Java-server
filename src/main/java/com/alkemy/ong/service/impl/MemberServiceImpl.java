package com.alkemy.ong.service.impl;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private final MessageSource messageSource;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MemberDto saveMember(MemberDto memberDto) {
        if(memberDto.getName() == null) throw new BadRequestException(messageSource.getMessage("member.name.empty", null, Locale.ENGLISH));
        if(memberDto.getName().matches(".*[0-9].*")) throw new BadRequestException(messageSource.getMessage("member.error.name",new Object[]{memberDto.getName()}, Locale.ENGLISH));
        Member member = memberRepository.save(memberMapper.creationMember(memberDto));
        return memberMapper.memberToDto(member);
    }

    @Override
    public Map<String, String> deleteMemberById(Long id) {
        Optional.ofNullable(memberRepository.findByIdMember(id)).orElseThrow(()->  new NotFoundException(messageSource.getMessage("member.not.found", null, Locale.ENGLISH))).setDeleted(true);
        return new HashMap<>(){{put("message", messageSource.getMessage("member.delete.sucessfuly", new Object[]{id.toString()}, Locale.ENGLISH));}};
    }
}
