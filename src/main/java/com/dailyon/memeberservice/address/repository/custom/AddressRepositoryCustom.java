package com.dailyon.memeberservice.address.repository.custom;

import com.dailyon.memeberservice.address.api.response.AddressGetResponse;

import java.util.List;

public interface AddressRepositoryCustom{
    List<AddressGetResponse> findByMemberId(Long memberId);
}
