package com.dailyon.memeberservice.address.repository.custom;

import com.dailyon.memeberservice.address.api.response.AddressGetRequest;

import java.util.List;

public interface AddressRepositoryCustom{
    List<AddressGetRequest> findByMemberId(Long memberId);
}
