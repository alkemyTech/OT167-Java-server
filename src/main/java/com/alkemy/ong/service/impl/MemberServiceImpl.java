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

import java.util.List;
import java.util.Locale;

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
    public List<MemberDto> listAllMembers() {
        List<Member> memberList = memberRepository.findAll();
        if(memberList.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("members.not.found",null, Locale.ENGLISH));
        }
        return memberMapper.memberListToDtoList(memberList);
    }
}
