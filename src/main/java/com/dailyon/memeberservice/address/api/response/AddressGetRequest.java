package com.dailyon.memeberservice.address.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressGetRequest {

    private boolean isDefault;

    private String name;

    private String detailAddress;

    private String roadAddress;

    private String postCode;

    private String phoneNumber;

}
