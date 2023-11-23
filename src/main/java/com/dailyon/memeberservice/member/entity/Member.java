package com.dailyon.memeberservice.member.entity;

import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String profileImgUrl;
    private String gender;
    private String birth;
    private String createdAt;
    private String updatedAt;
    private String code;
    private Long point;
    private boolean isDeleted;

    public Member() {

    }


    @Builder
    private Member (
        Long id,
        String email,
        String profileImgUrl,
        String gender,
        String birth,
        String createdAt,
        String updatedAt,
        String code,
        Long point,
        boolean isDelted) {
        this.id = id;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.birth = birth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.code = code;
        this.point = point;
    }



    public void changeMember (
            String email,
            String profileImgUrl,
            String birth,
            String gender,
            String updatedAt) {
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.birth = birth;
        this.updatedAt = updatedAt;
    }


    public void softDelete() {
        this.isDeleted = true;
    }
}
