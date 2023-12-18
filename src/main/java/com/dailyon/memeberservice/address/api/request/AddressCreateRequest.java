package com.dailyon.memeberservice.address.api.request;

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

    private Boolean isDefault;

    private String name;

    private String detailAddress;

    private String roadAddress;

    private String postCode;

    private String phoneNumber;

}
