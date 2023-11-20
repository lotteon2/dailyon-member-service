package com.dailyon.memeberservice.member.service;

import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import com.dailyon.memeberservice.member.api.request.MemberModifyRequest;
import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long registerMember(MemberCreateRequest request){
        String profileImgUrl = Optional.ofNullable(request.getProfileImgUrl())
                .orElse("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flaticon.com%2Fkr%2Ffree-icon%2Fgithub-logo_25231&psig=AOvVaw0pcr5--c2h4nyBbrQ9pzPb&ust=1700566691294000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCICL2t--0oIDFQAAAAAdAAAAABAD");
        String gender = Optional.ofNullable(request.getGender())
                .orElse("");
        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        String updatedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        String code = UUID.randomUUID().toString();

        Member member = Member.builder()
                .email(request.getEmail())
                .profileImgUrl(profileImgUrl)
                .gender(gender)
                .birth(request.getBirth())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .code(code)
                .build();
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Long modifyMember(MemberModifyRequest request, Long id){
        System.out.println("############################");
        System.out.println(id);
        System.out.println("############################");
        Member member = memberRepository.findById(id).orElseThrow();

        System.out.println("############################");
        System.out.println(member);
        System.out.println("############################");

        String updatedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME); // 현재 날짜로 설정

        String profileImgUrl = Optional.ofNullable(request.getProfileImgUrl())
                .orElse(member.getProfileImgUrl()); // 기존값으로 설정
        String gender = Optional.ofNullable(request.getGender())
                .orElse(member.getGender()); // 기존값으로 설정
        String birth = Optional.ofNullable(request.getBirth())
                .orElse(member.getBirth()); // 기존값으로 설정

        member.changeMember(
                request.getEmail(),
                profileImgUrl,
                birth,
                gender,
                updatedAt
        );

        return id;
    }


    @Transactional
    public Long softDelete(Long id){
        Member member = memberRepository.findById(id).orElseThrow();
        member.softDelete();

        return id;
    }


}
