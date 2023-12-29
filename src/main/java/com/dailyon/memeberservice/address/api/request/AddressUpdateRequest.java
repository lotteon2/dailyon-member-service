package com.dailyon.memeberservice.address.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateRequest {

    private Long addressId;

    private String name;

    private String detailAddress;

    private String roadAddress;

    private String postCode;

    private String phoneNumber;

}
