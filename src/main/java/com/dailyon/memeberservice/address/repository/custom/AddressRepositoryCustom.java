package com.dailyon.memeberservice.address.repository.custom;

import com.dailyon.memeberservice.address.api.response.AddressGetResponse;
import com.dailyon.memeberservice.address.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AddressRepositoryCustom{
    List<Address> findByMemberId(Long memberId);


    Page<Address> findByMemberId(Long memberId, Pageable pageable);

    Address findFirstByMemberIdAndIsDefault(Long memberId, boolean isDefault);
}
