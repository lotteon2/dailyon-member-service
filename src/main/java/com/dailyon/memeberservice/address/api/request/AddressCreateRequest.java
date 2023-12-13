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

    private boolean isDefault;

    private String name;

    private String detailAddress;

    private String roadAddress;

    private String postCode;

    private String phoneNumber;

}
