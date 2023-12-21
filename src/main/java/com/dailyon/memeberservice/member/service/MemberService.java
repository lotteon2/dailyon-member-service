package com.dailyon.memeberservice.member.service;

import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import com.dailyon.memeberservice.member.api.response.MemberGetResponse;
import com.dailyon.memeberservice.member.api.request.MemberModifyRequest;
import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long registerMember(MemberCreateRequest request){
        String profileImgUrl = Optional.ofNullable(request.getProfileImgUrl())
                .orElse("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flaticon.com%2Fkr%2Ffree-icon%2Fgithub-logo_25231&psig=AOvVaw0pcr5--c2h4nyBbrQ9pzPb&ust=1700566691294000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCICL2t--0oIDFQAAAAAdAAAAABAD");
        String gender = Optional.ofNullable(request.getGender())
                .orElse("");
        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .profileImgUrl(profileImgUrl)
                .gender(gender)
                .birth(request.getBirth())
                .code(uuid)
                .point(0L)
                .isDelted(false)
                .build();
        memberRepository.save(member);

        return member.getId();
    }

    public MemberGetResponse getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        MemberGetResponse response = new MemberGetResponse();

        response.setMemberId(member.getId());
        response.setNickname(member.getNickname());
        response.setEmail(member.getEmail());
        response.setProfileImgUrl(member.getProfileImgUrl());
        response.setBirth(member.getBirth());
        response.setGender(member.getGender());
        response.setPoint(member.getPoint());

        return response;
    }

    public boolean MemberDuplicateCheck(String email){
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public Long modifyMember(MemberModifyRequest request, Long id){
        Member member = memberRepository.findById(id).orElseThrow();

        String nickname = Optional.ofNullable(request.getNickname())
                .orElse(member.getNickname());
        String profileImgUrl = Optional.ofNullable(request.getProfileImgUrl())
                .orElse(member.getProfileImgUrl());
        String gender = Optional.ofNullable(request.getGender())
                .orElse(member.getGender());
        String birth = Optional.ofNullable(request.getBirth())
                .orElse(member.getBirth());

        member.changeMember(
                nickname,
                profileImgUrl,
                birth,
                gender
        );

        return id;
    }


    @Transactional
    public Long softDelete(Long id){
        Member member = memberRepository.findById(id).orElseThrow();
        member.softDelete();

        return id;
    }

    public Long getPoints(Long memberId) {
        Long point = memberRepository.findPointsById(memberId);
        return point;
    }
}
