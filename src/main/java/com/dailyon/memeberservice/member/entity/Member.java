package com.dailyon.memeberservice.member.entity;

import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String profileImgUrl;

    private String gender;

    private String birth;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private String updatedAt;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Long point;

    @Column(nullable = false)
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

    public void changePoint(Long amount) {
        if (this.point + amount < 0) {
            throw new RuntimeException("Not enough points");
        }
        this.point += amount;
    }


    public void softDelete() {
        this.isDeleted = true;
    }
}
