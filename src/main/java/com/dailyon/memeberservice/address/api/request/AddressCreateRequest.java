package com.dailyon.memeberservice.address.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateRequest {

    private Long id;

    private Long member_id;

    private boolean is_default;

    private String name;

    private String detail_address;

    private String road_address;

    private String post_code;

    private String phone_number;

    private LocalDateTime  createdAt;

    private LocalDateTime updatedAt;

}
