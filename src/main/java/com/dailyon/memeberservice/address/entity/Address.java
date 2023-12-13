package com.dailyon.memeberservice.address.entity;

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

    @Column(nullable = false)
    private Long member_id;

    @Column(nullable = false)
    private boolean is_default;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String detail_address;

    @Column(nullable = false)
    private String road_address;

    @Column(nullable = false)
    private String post_code;

    @Column(nullable = false)
    private String phone_number;


    @Column(nullable = false, columnDefinition = "timestamp default now()")
    @CreatedDate
    private LocalDateTime  createdAt;

    @Column(nullable = false, columnDefinition = "timestamp default now()")
    @LastModifiedDate
    private LocalDateTime updatedAt;


    public Address() {

    }

    @Builder
    public Address(Long id, Long member_id, boolean is_default, String name,
                   String detail_address, String road_address, String post_code,
                   String phone_number, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.member_id = member_id;
        this.is_default = is_default;
        this.name = name;
        this.detail_address = detail_address;
        this.road_address = road_address;
        this.post_code = post_code;
        this.phone_number = phone_number;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
