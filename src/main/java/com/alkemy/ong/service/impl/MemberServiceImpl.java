package com.alkemy.ong.service.impl;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.PaginationMessage;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private static final int SIZE_PAG_10 = 9;
    @Autowired
    private PaginationMessage paginationMessage;
    private WebRequest request;

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
    public MessagePag getAllMembers(int page, WebRequest request) {
        Page membersPage = memberRepository.findAll(PageRequest.of(page, SIZE_PAG_10));
        return paginationMessage.messageInfo(membersPage, memberMapper.memberListToDtoList(membersPage.getContent()), request);
    }

    @Override
    public void deleteMemberById(Long id) {
        Optional.ofNullable(memberRepository.findById(id)).get().orElseThrow(()->  new NotFoundException(messageSource.getMessage("member.not.found", null, Locale.ENGLISH))).setDeleted(true);
    }
}
