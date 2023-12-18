package com.dailyon.memeberservice.address.entity;

import com.dailyon.memeberservice.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean isDefault;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String postCode;

    @Column(nullable = false)
    private String phoneNumber;


    @Column(nullable = false, columnDefinition = "timestamp default now()")
    @CreatedDate
    private LocalDateTime  createdAt;

    @Column(nullable = false, columnDefinition = "timestamp default now()")
    @LastModifiedDate
    private LocalDateTime updatedAt;


    public Address() {

    }

    @Builder
    public Address(Long id, Member member, Boolean isDefault, String name,
                   String detailAddress, String roadAddress, String postCode,
                   String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.member = member;
        this.isDefault = isDefault;
        this.name = name;
        this.detailAddress = detailAddress;
        this.roadAddress = roadAddress;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
