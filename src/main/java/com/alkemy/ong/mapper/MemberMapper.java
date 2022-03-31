package com.alkemy.ong.mapper;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class MemberMapper {
    public MemberDto memberToDto (Member member){
        MemberDto memberDto = new MemberDto();
        memberDto.setIdMember(member.getIdMember());
        memberDto.setName(member.getName());
        memberDto.setFacebookUrl(member.getFacebookUrl());
        memberDto.setInstagramUrl(member.getInstagramUrl());
        memberDto.setLinkedinUrl(member.getLinkedinUrl());
        memberDto.setImage(member.getImage());
        memberDto.setDescription(member.getDescription());
        memberDto.setUpdateDate(member.getUpdateDate());
        memberDto.setCreationDate(member.getCreationDate());
        return memberDto;
    }
    public List<MemberDto> memberListToDtoList (List<Member> membersList){
        List<MemberDto> membersToDtoList = new ArrayList<>();
        membersList.forEach(m -> membersToDtoList.add(memberToDto(m)));
        return membersToDtoList;
    }
    public Member creationMember(MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setFacebookUrl(memberDto.getFacebookUrl());
        member.setInstagramUrl(memberDto.getInstagramUrl());
        member.setLinkedinUrl(memberDto.getLinkedinUrl());
        member.setImage(memberDto.getImage());
        member.setDescription(memberDto.getDescription());
        return member;
    }
}
