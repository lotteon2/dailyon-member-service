package com.dailyon.memeberservice.address.repository.custom;

import com.dailyon.memeberservice.address.api.response.AddressGetResponse;
import com.dailyon.memeberservice.address.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressRepositoryCustom{
    List<Address> findByMemberId(Long memberId);


    Page<Address> findByMemberId(Long memberId, Pageable pageable);
}
