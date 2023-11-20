package com.dailyon.memeberservice.member.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberModifyRequest{

    @NotNull(message = "email null error")
    private String email;

    @NotNull(message = "profileImgUrl null error")
    private String profileImgUrl;

    @NotNull(message = "gender null error")
    private String gender;

    @NotNull(message = "birth null error")
    private String birth;


}