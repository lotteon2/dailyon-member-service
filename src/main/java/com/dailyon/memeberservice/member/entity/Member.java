package com.dailyon.memeberservice.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@NoArgsConstructor
@Entity
public class Member {
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
}
