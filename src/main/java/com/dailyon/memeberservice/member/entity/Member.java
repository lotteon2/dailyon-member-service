package com.dailyon.memeberservice.member.entity;

import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImgUrl;

    private String gender;

    private String birth;

    @Column(nullable = false, columnDefinition = "timestamp default now()")
    @CreatedDate
    private LocalDateTime  createdAt;

    @Column(nullable = false, columnDefinition = "timestamp default now()")
    @LastModifiedDate
    private LocalDateTime updatedAt;

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
        String nickname,
        String profileImgUrl,
        String gender,
        String birth,
        String code,
        Long point,
        LocalDateTime createdAt,
        boolean isDelted) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.birth = birth;
        this.code = code;
        this.point = point;
        this.createdAt = createdAt;
    }



    public void changeMember (
            String nickname,
            String birth,
            String gender
    ) {
        this.email = email;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.birth = birth;
    }

    public void changePoint(Long amount) {
        if (this.point + amount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough points");
        }
        this.point += amount;
    }


    public void softDelete() {
        this.isDeleted = true;
    }
}
