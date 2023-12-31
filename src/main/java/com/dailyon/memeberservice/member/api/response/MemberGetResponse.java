package com.dailyon.memeberservice.member.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberGetResponse {

    private Long memberId;

    private String email;

    private String nickname;

    private String profileImgUrl;

    private String gender;

    private String birth;

    private Long point;

    private boolean isDeleted;
}
