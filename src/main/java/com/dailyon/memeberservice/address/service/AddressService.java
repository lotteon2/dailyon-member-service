package com.dailyon.memeberservice.address.service;

import com.dailyon.memeberservice.address.entity.Address;
import com.dailyon.memeberservice.address.entity.AddressCreateRequest;
import com.dailyon.memeberservice.address.repository.AddressRepository;
import com.dailyon.memeberservice.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Long createAddress(AddressCreateRequest request, Long memberId){

        Address address = Address.builder()
                        .memberId(Member.builder().id(memberId).build())
                        .isDefault(request.isDefault())
                        .name((request.getName()))
                        .detailAddress(request.getDetailAddress())
                        .roadAddress(request.getRoadAddress())
                        .postCode(request.getPostCode())
                        .phoneNumber(request.getPhoneNumber())
                        .build();

        addressRepository.save(address);

        return address.getId();
    }
}
