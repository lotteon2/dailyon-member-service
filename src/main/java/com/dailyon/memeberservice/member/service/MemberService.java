package com.dailyon.memeberservice.member.service;

import com.amazonaws.auth.policy.Resource;
import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import com.dailyon.memeberservice.member.api.response.MemberGetResponse;
import com.dailyon.memeberservice.member.api.request.MemberModifyRequest;
import com.dailyon.memeberservice.member.config.S3Util;
import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.kafka.MemberKafkaHandler;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import com.dailyon.memeberservice.member.sqs.UserCreatedSqsProducer;
import dailyon.domain.sns.kafka.dto.MemberCreateDTO;
import dailyon.domain.sns.kafka.dto.MemberUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberKafkaHandler memberKafkaHandler;
    private final UserCreatedSqsProducer userCreatedSqsProducer;
    private final S3Util s3Util;

    @Transactional
    public Long registerMember(MemberCreateRequest request){
        String profileImgUrl = Optional.ofNullable(request.getProfileImgUrl())
                .orElse("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flaticon.com%2Fkr%2Ffree-icon%2Fgithub-logo_25231&psig=AOvVaw0pcr5--c2h4nyBbrQ9pzPb&ust=1700566691294000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCICL2t--0oIDFQAAAAAdAAAAABAD");
        String gender = Optional.ofNullable(request.getGender())
                .orElse("");
        String uuid = UUID.randomUUID().toString();


        File tempFile = new File("temp_image.jpg");
        try {
            FileUtils.copyURLToFile(new URL(profileImgUrl), tempFile);
        } catch (IOException e) {
            log.error("Failed to copy URL to file", e);
        }

        String fileName = s3Util.createFilePath(tempFile.getName());
        s3Util.uploadImage(tempFile, fileName);

        tempFile.delete();


        Member member = Member.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .profileImgUrl(fileName)
                .gender(gender)
                .birth(request.getBirth())
                .code(uuid)
                .point(0L)
                .isDelted(false)
                .build();
        memberRepository.save(member);

        MemberCreateDTO memberCreateDTO = MemberCreateDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImgUrl(member.getProfileImgUrl())
                .code(member.getCode())
                .build();
        memberKafkaHandler.memberCreateUseSuccessMessage(memberCreateDTO);

        // TODO: sqs 유저생성 발송
        userCreatedSqsProducer.produce(member.getId());

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
        response.setDeleted(member.isDeleted());

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
        String gender = Optional.ofNullable(request.getGender())
                .orElse(member.getGender());
        String birth = Optional.ofNullable(request.getBirth())
                .orElse(member.getBirth());

        member.changeMember(
                nickname,
                birth,
                gender
        );

        MemberUpdateDTO memberUpdateDTO = MemberUpdateDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImgUrl(member.getProfileImgUrl())
                .build();
        memberKafkaHandler.memberUpdateUseSuccessMessage(memberUpdateDTO);

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


    public String geturl(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();

        String memberUrl = s3Util.getPreSignedUrl(member.getProfileImgUrl());

        return memberUrl;

    }
}
