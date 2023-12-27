package com.dailyon.memeberservice.member.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequest {

    private String email;

    private String nickname;

    private String profileImgUrl;

    private String gender;

    private String birth;


}