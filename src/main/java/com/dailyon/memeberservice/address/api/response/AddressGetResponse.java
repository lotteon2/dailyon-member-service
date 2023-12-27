package com.dailyon.memeberservice.address.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressGetResponse {
    private Long id;

    private Boolean isDefault;

    private String name;

    private String detailAddress;

    private String roadAddress;

    private String postCode;

    private String phoneNumber;

}
