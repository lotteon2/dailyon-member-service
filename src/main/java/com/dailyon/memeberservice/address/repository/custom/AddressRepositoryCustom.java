package com.dailyon.memeberservice.address.repository.custom;

import com.dailyon.memeberservice.address.api.response.AddressGetResponse;
import com.dailyon.memeberservice.address.entity.Address;

import java.util.List;

public interface AddressRepositoryCustom{
    List<Address> findByMemberId(Long memberId);
}
